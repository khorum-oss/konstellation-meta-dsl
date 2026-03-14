rootProject.name = "meta-dsl"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://open-reliquary.nyc3.cdn.digitaloceanspaces.com")
        }
    }
}