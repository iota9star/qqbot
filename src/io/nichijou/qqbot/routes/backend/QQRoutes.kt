package io.nichijou.qqbot.routes.backend

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.nichijou.qqbot.model.resp.FriendResp
import io.nichijou.qqbot.model.resp.GroupResp
import io.nichijou.qqbot.model.resp.QQResp
import io.nichijou.qqbot.model.resp.R
import net.mamoe.mirai.Bot

fun Route.qqRoutes() {
  route("/qq") {
    get("/list") {
      val list = Bot.botInstances.map {
        QQResp(id = it.id, nickname = it.nick, online = it.isOnline, friends = it.friends.size, groups = it.groups.size)
      }
      call.respond(R.ok(list))
    }
    get("/{qq}/friends") {
      val qq = call.parameters["qq"]!!.toLong()
      val bot = Bot.getInstance(qq)
      val friends = bot.friends.map { FriendResp(id = it.id, nickname = it.nick, avatar = it.avatarUrl) }
      call.respond(R.ok(friends))
    }

    get("/{qq}/groups") {
      val qq = call.parameters["qq"]!!.toLong()
      val bot = Bot.getInstance(qq)
      val friends =
        bot.groups.map { GroupResp(id = it.id, name = it.name, avatar = it.avatarUrl, members = it.members.size) }
      call.respond(R.ok(friends))
    }

    patch("/login/{qq}") {
    }
  }
}
