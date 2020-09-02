package io.nichijou

import java.util.*
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.event.subscribeFriendMessages
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.join

class QQBot(private val conf: QQConf) : CoroutineScope {
    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.IO

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

    companion object {
        fun bot(id: Long) = Bot.getInstance(id)
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

data class QQ(val qq: Long, val password: String)

data class Message(
  val id: Long,
  val bot: Long,
  val whoQQ: Long,
  val whoNickname: String,
  val whoAvatar: String,
  val whoSay: String,
  val date: Date
)
