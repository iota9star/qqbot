package io.nichijou

interface Conf {
    fun validate() {}
}

class HikariConf : Conf {
    var schema: String? = null
    var driverClassName: String? = null
    var jdbcUrl: String? = null
    var username: String? = null
    var password: String? = null
    var poolName: String? = null
    var maximumPoolSize: Int? = null

    override fun validate() {
        if (this.driverClassName.isNullOrBlank()) {
            throw ConfPropertyRequiredException("")
        }
        if (this.jdbcUrl.isNullOrBlank()) {
            throw ConfPropertyRequiredException("")
        }
        if (this.username.isNullOrBlank()) {
            throw ConfPropertyRequiredException("")
        }
    }
}

class DeploymentConf : Conf {
    var contextPath: String? = null
    var host: String? = null
    var port: Int? = null
}

class EmailConf : Conf {
    var host: String? = null
    var port: Int? = null
    var sender: String? = null
    var address: String? = null
    var password: String? = null
    var verifySubject: String? = null
    var debugLogging: Boolean? = null
    var sessionTimeout: Int? = null
    override fun validate() {
        if (this.host.isNullOrBlank()) {
            throw ConfPropertyRequiredException("")
        }
        if (this.port == null || this.port!! <= 0) {
            throw ConfPropertyRequiredException("")
        }
        if (verifySubject.isNullOrBlank()) {
            throw ConfPropertyRequiredException("")
        }
        if (address.isNullOrBlank()) {
            throw ConfPropertyRequiredException("")
        }
        if (this.password.isNullOrBlank()) {
            throw ConfPropertyRequiredException("")
        }
    }
}

class QQConf : Conf {
    var accounts: List<Account>? = null

    data class Account(val qq: Long, val password: String)

    override fun validate() {
    }
}

class ConfPropertyRequiredException(message: String) : RuntimeException(message)
