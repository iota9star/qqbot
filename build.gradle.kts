val ktor_version: String by project
val kotlin_version: String by project
val kotlin_coroutines_version: String by project
val logback_version: String by project
val h2_version: String by project
val koin_version: String by project
val mail_version: String by project
val cli_version: String by project
val exposed_version: String by project

plugins {
    application
    kotlin("jvm") version "1.4.0"
    kotlin("kapt") version "1.4.0"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("com.diffplug.gradle.spotless") version "3.30.0"
}

group = "io.nichijou"
version = "0.0.1"

application {
  mainClassName = "io.nichijou.qqbot.LauncherKt"
}

repositories {
    mavenLocal()
    jcenter()
    google()
    maven { url = uri("https://plugins.gradle.org/m2/") }
    maven { url = uri("https://kotlin.bintray.com/kotlin-eap") }
    maven { url = uri("https://kotlin.bintray.com/kotlinx") }
    maven { url = uri("https://kotlin.bintray.com/ktor") }
    maven { url = uri("https://kotlin.bintray.com/exposed") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlin_coroutines_version")
    implementation("io.ktor:ktor-server-cio:$ktor_version")
    implementation("io.ktor:ktor-websockets:$ktor_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-mustache:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-locations:$ktor_version")
    implementation("io.ktor:ktor-metrics:$ktor_version")
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-jackson:$ktor_version")
  implementation("io.ktor:ktor-client-core:$ktor_version")
  implementation("io.ktor:ktor-client-core-jvm:$ktor_version")
  implementation("io.ktor:ktor-client-cio:$ktor_version")
  implementation("io.ktor:ktor-client-logging-jvm:$ktor_version")
  implementation("ch.qos.logback:logback-classic:$logback_version")
  testImplementation("io.ktor:ktor-server-tests:$ktor_version")

  implementation("com.h2database:h2:$h2_version")
  implementation("com.zaxxer:HikariCP:latest.release")

  implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-jodatime:$exposed_version")

  implementation("com.github.ajalt.clikt:clikt:$cli_version")

  // mirai
  implementation("net.mamoe:mirai-core:latest.integration")
  implementation("net.mamoe:mirai-core-qqandroid-jvm:latest.integration")

  implementation("org.koin:koin-ktor:$koin_version")
  implementation("org.koin:koin-logger-slf4j:$koin_version")

  implementation("org.simplejavamail:simple-java-mail:$mail_version")
  implementation("org.simplejavamail:batch-module:$mail_version")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].java.srcDirs("src")
sourceSets["test"].java.srcDirs("src")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

kapt {
    useBuildCache = false
}

spotless {
    kotlin {
        ktlint().userData(
            mapOf(
                "indent_size" to "2",
                "continuation_indent_size" to "2",
                "disabled_rules" to "no-wildcard-imports,experimental:annotation"
            )
        )
    }

    kotlinGradle {
        ktlint().userData(
            mapOf(
                "indent_size" to "2",
                "continuation_indent_size" to "2",
                "disabled_rules" to "no-wildcard-imports,experimental:annotation"
            )
        )
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    shadowJar {
        dependsOn(spotlessApply)
//        minimize()
    }
//  register<proguard.gradle.ProGuardTask>("proguardJar") {
//    dependsOn(shadowJar)
//    injars("build/libs/qqbot-$version-all.jar")
//    outjars("build/libs/qqbot-$version-min.jar")
//    libraryjars(System.getProperty("java.home") + "/lib/rt.jar")
//    printmapping("build/libs/qqbot-$version-min.map")
//    ignorewarnings()
//    dontobfuscate()
//    dontoptimize()
//    dontwarn()
//    val keepClasses = listOf(
//      "io.ktor.server.netty.EngineMain", // The EngineMain you use, netty in this case.
//      "kotlin.reflect.jvm.internal.**",
//      "io.nichijou.LauncherKt", // The class containing your module defined in the application.conf
//      "kotlin.text.RegexOption"
//    )
//    keepClasses.forEach {
//      keep(mapOf("access" to "public", "name" to it), closureOf<Any> {
//        method(mapOf("access" to "public"))
//        method(mapOf("access" to "private"))
//      })
//    }
//  }
}
tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf("Main-Class" to application.mainClassName)
        )
    }
}
