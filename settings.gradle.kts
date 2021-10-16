rootProject.name = "kobby-gradle-tutorial"

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    }
}

include("cinema-api")
include("cinema-server")
include("cinema-client")