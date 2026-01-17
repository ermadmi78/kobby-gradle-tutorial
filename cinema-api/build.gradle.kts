description = "Cinema API"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("io.github.ermadmi78.kobby") version "5.3.1"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Add this dependency to enable Kotlinx Serialization
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Add this dependency to enable default Ktor adapters generation
    compileOnly("io.ktor:ktor-client-cio:2.3.13")
}