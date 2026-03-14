import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.named
import org.jetbrains.dokka.gradle.DokkaTask
import org.khorum.oss.plugins.open.secrets.getPropertyOrEnv
import java.net.URI
import java.util.Properties
import kotlin.apply
import kotlin.collections.component1
import kotlin.collections.component2

plugins {
    kotlin("jvm") version "2.1.20"
    id("org.jetbrains.dokka") version "1.9.20"
    id("com.google.devtools.ksp") version "2.1.20-1.0.32"
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
    id("org.sonarqube") version "7.0.0.6105"
    id("org.khorum.oss.plugins.open.pipeline") version "1.0.3"
    id("org.khorum.oss.plugins.open.secrets") version "1.0.3"
    signing

    id("org.khorum.oss.plugins.open.publishing.maven-generated-artifacts") version "1.0.5"
    id("org.khorum.oss.plugins.open.publishing.digital-ocean-spaces") version "1.0.5"
}

group = "org.khorum.oss.konstellation"
version = file("VERSION").readText().trim()

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

sharedRepositories()

val loggingVersion = "4.0.0-beta-2"
val jupiterApiVersion = "6.0.0-M1"

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.github.microutils:kotlin-logging:$loggingVersion")

    testImplementation(kotlin("test")) // Kotlin’s own assert functions, optional but handy
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterApiVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

fun Project.sharedRepositories() {
    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://www.jetbrains.com/intellij-repository/releases") }
    }
}

val secretPropsFile = project.rootProject.file("secret.properties") // update to your secret file under `buildSrc`
val ext = project.extensions.extraProperties
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply { load(it) }
    }.onEach { (name, value) ->
        ext[name.toString()] = value
    }
    project.logger.log(LogLevel.LIFECYCLE, "Secrets loaded from file: $ext")
}


tasks.named<DokkaTask>("dokkaJavadoc") {
    dokkaSourceSets {
        named("main") {
            includeNonPublic.set(true)
            skipDeprecated.set(true)
            jdkVersion.set(17)
            sourceLink {
                val uri: URI = URI.create("https://github.com/violabs/konstellation")
                this.remoteUrl.set(uri.toURL())
                this.remoteLineSuffix.set("#L")
                this.localDirectory.set(project.projectDir)
            }
        }
    }
}

signing {
    useInMemoryPgpKeys(
        providers.environmentVariable("GPG_SIGNING_KEY").orNull,
        providers.environmentVariable("GPG_SIGNING_PASSWORD").orNull
    )
    sign(publishing.publications)
}

tasks.withType<PublishToMavenRepository>().configureEach {
    dependsOn(tasks.withType<Sign>())
}

digitalOceanSpacesPublishing {
    bucket = "open-reliquary"
    accessKey = project.getPropertyOrEnv("spaces.key", "DO_SPACES_API_KEY")
    secretKey = project.getPropertyOrEnv("spaces.secret", "DO_SPACES_SECRET")
    publishedVersion = version.toString()
    signingRequired = true
}

mavenGeneratedArtifacts {
    publicationName = "digitalOceanSpaces"
    name = "Konstellation DSL Builder Metadata"
    description = """
            Classes for use alongside the Konstellation DSL Builder.
        """
    websiteUrl = "https://github.com/violabs/konstellation/tree/main/meta-dsl"

    licenses {
        license {
            name = "Apache License, Version 2.0"
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        }
    }

    developers {
        developer {
            id = "violabs"
            name = "Violabs Team"
            email = "support@violabs.io"
            organization = "Violabs Software"
        }
    }

    scm {
        connection = "https://github.com/violabs/konstellation.git"
    }
}

sonar {
    properties {
        property("sonar.projectKey", "khorum-oss_konstellation-meta-dsl")
        property("sonar.organization", "khorum-oss")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory.get()}/reports/kover/report.xml")
    }
}