package io.nichijou

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import net.mamoe.mirai.Bot

fun Application.routes() {
    routing {
        post("/github-hooks") {
            val result = call.receiveOrNull<Map<*, *>>()
            Bot.getInstance(1).getGroup(1).sendMessage(result.toString())
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
    }
}
