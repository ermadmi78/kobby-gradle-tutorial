description = "Cinema API"

plugins {
    kotlin("jvm")
    id("io.github.ermadmi78.kobby") version "2.1.1"
}

dependencies {
    // Add this dependency to enable Jackson annotation generation in DTO classes
    compileOnly("com.fasterxml.jackson.core:jackson-annotations:2.13.4")

    // Add this dependency to enable default Ktor adapters generation
    compileOnly("io.ktor:ktor-client-cio:2.1.2")
}