package io.nichijou.qqbot.routes.bot

import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import net.mamoe.mirai.Bot
import org.koin.ktor.ext.inject


fun Route.githubRoutes() {
  route("/github") {
    val objectMapper by inject<ObjectMapper>()
    post("/hooks") {
      val result = call.receiveOrNull<Map<*, *>>()
      if (result != null) {
        Bot.getInstance(1).getGroup(1)?.sendMessage(objectMapper.writeValueAsString(result))
      }
      call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
    }
  }
}
