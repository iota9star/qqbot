package io.nichijou.qqbot

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.event.subscribeFriendMessages
import net.mamoe.mirai.utils.BotConfiguration

class QQBot(private val conf: QQConf) {

  init {
    GlobalScope.launch {
      login()
    }
  }

  private suspend fun login() {
    conf.accounts?.map {
      BotFactory.newBot(it.qq, it.password) {
        fileBasedDeviceInfo()
        protocol = BotConfiguration.MiraiProtocol.ANDROID_PAD
      }.alsoLogin()
    }?.forEach {
      handleBot(it)
    }
  }

  suspend fun handleBot(bot: Bot) {
    bot.eventChannel.subscribeFriendMessages {
      "hello"{

      }
      "你好" reply "憨憨"
    }
    bot.join()
  }
}
