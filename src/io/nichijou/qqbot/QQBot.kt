package io.nichijou.qqbot

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.event.subscribeFriendMessages
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.join

class QQBot(private val conf: QQConf) {

  init {
    GlobalScope.launch {
      login()
    }
  }

  private suspend fun login() {
    conf.accounts?.map {
      Bot(it.qq, it.password) {
        fileBasedDeviceInfo()
      }.alsoLogin()
    }?.forEach {
      handleBot(it)
    }
  }

  suspend fun handleBot(bot: Bot) {
    bot.subscribeMessages {
    }
    bot.subscribeFriendMessages {
      contains("abc") {
        reply("abc")
      }
    }
    bot.join()
  }
}
