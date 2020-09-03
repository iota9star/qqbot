package io.nichijou.qqbot

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.MustacheFactory
import io.ktor.config.*
import org.koin.dsl.module
import org.simplejavamail.api.mailer.Mailer
import org.simplejavamail.api.mailer.MailerGenericBuilder
import org.simplejavamail.mailer.MailerBuilder

val funModule = module {
  // QQ相关
  single(createdAtStart = true) {
    QQBot(get())
  }
  // jackson相关配置
  single {
    jacksonObjectMapper().apply {
      setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
        indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
        indentObjectsWith(DefaultIndenter("  ", "\n"))
      })
      setSerializationInclusion(JsonInclude.Include.NON_NULL)
      disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }
  }
  // 邮箱相关
  single<Mailer> {
    val conf = get<EmailConf>()
    MailerBuilder
      .withSMTPServer(conf.host, conf.port, conf.address, conf.password)
      .withSessionTimeout(conf.sessionTimeout ?: MailerGenericBuilder.DEFAULT_SESSION_TIMEOUT_MILLIS)
//      .withDebugLogging(conf.debugLogging ?: MailerGenericBuilder.DEFAULT_JAVAXMAIL_DEBUG)
      .async()
      .buildMailer()
  }

  // 模板引擎
  single<MustacheFactory> {
    DefaultMustacheFactory("templates/mustache")
  }
}

val confModules = module {
  single {
    val conf = get<ApplicationConfig>().getSpecialConfigs("hikari", HikariConf::class.java)
    conf.validate()
    conf
  }

  single {
    val conf = get<ApplicationConfig>().getSpecialConfigs("email", EmailConf::class.java)
    conf.validate()
    conf
  }

  single {
    val qq = get<ApplicationConfig>().config("ktor.qq")
    val accountsConfs = qq.configList("accounts")
    val accounts = accountsConfs.map {
      QQConf.Account(it.property("id").getString().toLong(), it.property("pwd").getString())
    }
    QQConf().apply {
      this.accounts = accounts
    }
  }
}
