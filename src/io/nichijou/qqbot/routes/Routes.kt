package io.nichijou.qqbot.routes

import io.ktor.application.*
import io.ktor.routing.*
import io.nichijou.qqbot.routes.backend.qqRoutes
import io.nichijou.qqbot.routes.bot.githubRoutes

fun Application.routes() {
  routing {
    route("/backend") {
      qqRoutes()
    }
    route("/bot") {
      githubRoutes()
    }
  }
}
