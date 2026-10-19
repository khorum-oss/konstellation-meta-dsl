package org.khorum.oss.konstellation.metaDsl.annotation

import kotlin.reflect.KClass

/**
 * Marks a class as DSL-constructible from a single primitive or value-type input.
 *
 * When applied to a class `Foo`, every property of type `Foo` inside a
 * `@GeneratedDsl` data class gains an overload that accepts [inputType] directly,
 * so the DSL caller can pass the raw value instead of constructing `Foo` explicitly.
 *
 * ### Target
 * **Class declarations only.** This annotation is matched by scanning class
 * declarations and looking up properties by their type name; placing it on a
 * property (`@field:`, `@property:`, …) is a compile error. A single class-level
 * declaration applies the transform to every use site of that type — there is
 * no per-property override.
 *
 * ### Parameters
 * - [inputType] — the primitive/value type the generated DSL accepts.
 *   The annotated class must expose a single-argument constructor taking this type.
 * - [transformTemplate] — optional KotlinPoet template wrapping the input. Use
 *   `%N` as a placeholder for the input parameter name. When blank, the generator
 *   emits `ClassName(%N)` by default. Override only when you need a non-default
 *   construction expression (e.g. a factory call or a wrapping type).
 *
 * ### Examples
 *
 * Default construction — omit [transformTemplate]:
 * ```
 * @SingleEntryTransformDsl<String>(String::class)
 * data class Stardate(val content: String)
 *
 * @RootDsl @GeneratedDsl
 * data class Log(val date: Stardate)
 *
 * // Generated DSL allows:
 * log { date("47634.44") }   // instead of: date(Stardate("47634.44"))
 * ```
 *
 * Custom construction via [transformTemplate] — `%N` is replaced with the
 * generated input parameter name:
 * ```
 * @SingleEntryTransformDsl<Double>(Double::class, "Power(%N)")
 * data class Power(val value: Double)
 *
 * @RootDsl @GeneratedDsl
 * data class Configuration(val provisionalDecayPerTick: Power)
 *
 * // Generated DSL allows:
 * configuration { provisionalDecayPerTick(0.05) }
 * ```
 *
 * ### Overloaded inputs
 * The annotation is [Repeatable], so a class can declare multiple transforms
 * to accept several primitive/value-type inputs. Each instance emits its own
 * DSL overload, so [inputType] must be distinct across stacked annotations.
 * Each [inputType] must have a corresponding usable construction path on the
 * annotated class (either a matching constructor for the default `ClassName(%N)`
 * shape, or a [transformTemplate] that compiles against that input).
 * ```
 * @SingleEntryTransformDsl<Int>(Int::class, "Power(%N.toDouble())")
 * @SingleEntryTransformDsl<Double>(Double::class, "Power(%N)")
 * data class Power(val value: Double)
 *
 * @RootDsl @GeneratedDsl
 * data class Configuration(val provisionalDecayPerTick: Power)
 *
 * // Generated DSL allows either:
 * configuration { provisionalDecayPerTick(1) }       // Int overload
 * configuration { provisionalDecayPerTick(0.05) }    // Double overload
 * ```
 */
@Repeatable
@Target(AnnotationTarget.CLASS)
annotation class SingleEntryTransformDsl<T : Any>(
    val inputType: KClass<T>,
    val transformTemplate: String = ""
)
