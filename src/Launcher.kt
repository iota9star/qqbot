package io.nichijou

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option

fun main(args: Array<String>): Unit = Launcher().main(args)

class Launcher : CliktCommand(name = "my-qq-bot") {
    private val active: String? by option(
        "-a",
        "--active",
        help = "Provide server use conf."
    ).default("dev")
    private val path: String? by option(
        "-p",
        "--path",
        help = "Provide server use conf file path."
    )

    override fun run() {
        EngineMain.run(CliArgs(active = active, path = path))
    }
}

data class CliArgs(
  val active: String?,
  val path: String?
)
