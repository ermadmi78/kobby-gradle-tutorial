description = "Cinema API"

plugins {
    kotlin("jvm")
    id("io.github.ermadmi78.kobby") version "1.4.1"
}

dependencies {
    // Add this dependency to enable Jackson annotation generation in DTO classes
    compileOnly("com.fasterxml.jackson.core:jackson-annotations:2.12.2")

    // Add this dependency to enable default Ktor adapters generation
    compileOnly("io.ktor:ktor-client-cio:1.5.4")
}