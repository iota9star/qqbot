package io.nichijou

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.config.*
import org.koin.dsl.module

val funModule = module {
    single(createdAtStart = true) {
        QQBot(get())
    }
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
