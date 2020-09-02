package io.nichijou

import io.ktor.application.*
import io.ktor.config.*

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    koin()
    db()
    routes()
    othersFeatures()
}

val Application.envKind get() = environment.config.property("ktor.environment").getString()

fun <T : Any> ApplicationConfig.getSpecialConfigs(domain: String, clazz: Class<T>): T {
    val config = this.config("ktor.$domain")
    val t = clazz.newInstance()
    val fields = clazz.declaredFields
    for (field in fields) {
        if (!field.isAccessible) {
            field.isAccessible = true
        }
        val value = config.propertyOrNull(field.name)?.getString() ?: continue
        when (field.genericType) {
            java.lang.String::class.java -> field.set(t, value)
            java.lang.Integer::class.java -> field.set(t, value.toInt())
            java.lang.Long::class.java -> field.set(t, value.toLong())
            java.lang.Boolean::class.java -> field.set(t, value.toBoolean())
            java.lang.Double::class.java -> field.set(t, value.toDouble())
            java.lang.Float::class.java -> field.set(t, value.toFloat())
        }
    }
    return t
}

fun <T : Any> Application.getSpecialConfigs(domain: String, clazz: Class<T>): T {
    return environment.config.getSpecialConfigs(domain, clazz)
}
