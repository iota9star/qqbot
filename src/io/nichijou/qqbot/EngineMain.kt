package io.nichijou.qqbot

import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.util.*
import org.slf4j.LoggerFactory

object EngineMain {
  private val log = LoggerFactory.getLogger("my-engine")

  fun run(cliArgs: CliArgs) {
    log.info("Get args => active = {}, path = {}", cliArgs.active, cliArgs.path)
    val resourceName = when {
      !cliArgs.path.isNullOrBlank() -> cliArgs.path
      !cliArgs.active.isNullOrBlank() -> "application-${cliArgs.active}.conf"
      else -> "application.conf"
    }
    log.info("Use conf file => {}", resourceName)
    val applicationEnvironment = applicationEngineEnvironment {
      this.log = EngineMain.log
      this.config = HoconApplicationConfig(ConfigFactory.load(resourceName))
      val deploymentConf = this.config.getSpecialConfigs("deployment", DeploymentConf::class.java)
      connector {
        host = deploymentConf.host ?: "localhost"
        port = deploymentConf.port ?: 8080
      }
      rootPath = deploymentConf.contextPath ?: "/"
    }
    val engine = CIOApplicationEngine(applicationEnvironment) {
      loadConfiguration(applicationEnvironment.config)
    }
    engine.addShutdownHook {
      engine.stop(3000, 5000)
    }
    engine.start(true)
  }

  @OptIn(KtorExperimentalAPI::class)
  private fun CIOApplicationEngine.Configuration.loadConfiguration(config: ApplicationConfig) {
    val deploymentConfig = config.config("ktor.deployment")
    loadCommonConfiguration(deploymentConfig)
    deploymentConfig.propertyOrNull("connectionIdleTimeoutSeconds")?.getString()?.toInt()?.let {
      connectionIdleTimeoutSeconds = it
    }
  }
}
