package io.nichijou

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.util.date.*
import org.koin.dsl.module
import org.koin.ktor.ext.*
import org.koin.logger.slf4jLogger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

fun Application.koin() {
    val log = LoggerFactory.getLogger("module-koin")

    install(Koin) {
        // Use SLF4J Koin Logger at Level.INFO
        slf4jLogger()
        // declare used modules
        modules(
            module {
                single {
                    // provide ApplicationConfig
                    environment.config
                }
            },
            funModule,
            confModules
        )
    }

    environment.monitor.subscribe(KoinApplicationStarted) {
        log.info("Koin started.")
    }
    environment.monitor.subscribe(KoinApplicationStopPreparing) {
        log.info("Koin stopping...")
    }
    environment.monitor.subscribe(KoinApplicationStopped) {
        log.info("Koin stopped.")
    }
}

fun Application.db() {
    val hikariConf by inject<HikariConf>()
    DBFactory.setup(hikariConf)
}

fun Application.othersFeatures(testing: Boolean = false) {
    install(Locations) {
    }

    install(Compression) {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    install(AutoHeadResponse)

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(ConditionalHeaders)

    install(CachingHeaders) {
        options { outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(
                    CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60),
                    expires = null as? GMTDate?
                )
                else -> null
            }
        }
    }

    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }

    install(ShutDownUrl.ApplicationCallFeature) {
        // The URL that will be intercepted (you can also use the application.conf's ktor.deployment.shutdown.url key)
        shutDownUrl = "/ktor/application/shutdown"
        // A function that will be executed to get the exit code of the process
        exitCodeSupplier = { 0 } // ApplicationCall.() -> Int
    }

    val objectMapper by inject<ObjectMapper>()
    install(ContentNegotiation) {
        jackson(mapper = objectMapper)
    }
}

fun ContentNegotiation.Configuration.jackson(contentType: ContentType = ContentType.Application.Json, mapper: ObjectMapper) {
    val converter = JacksonConverter(mapper)
    register(contentType, converter)
}
