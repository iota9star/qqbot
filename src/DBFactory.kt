package io.nichijou

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

object DBFactory {
    fun setup(hikariConf: HikariConf) {
        val hikariConfig = HikariConfig()
        hikariConfig.schema = hikariConf.schema
        hikariConfig.driverClassName = hikariConf.driverClassName
        hikariConfig.jdbcUrl = hikariConf.jdbcUrl
        hikariConfig.password = hikariConf.password
        hikariConfig.poolName = hikariConf.poolName
        hikariConfig.username = hikariConf.username
        hikariConfig.maximumPoolSize = hikariConf.maximumPoolSize ?: 16
        val hikariDataSource = HikariDataSource(hikariConfig)
        Database.connect(hikariDataSource)
        transaction {
            addLogger(Slf4jSqlDebugLogger)
//            SchemaUtils.create(
//            )
        }
    }
}
