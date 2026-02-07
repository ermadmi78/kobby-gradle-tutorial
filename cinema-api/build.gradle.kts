description = "Cinema API"

plugins {
    kotlin("jvm")
    id("io.github.ermadmi78.kobby") version "5.3.1"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    // Add this dependency to enable Jackson annotation generation in DTO classes
    compileOnly("com.fasterxml.jackson.core:jackson-annotations:2.15.4")

    // Add this dependency to enable default Ktor adapters generation
    compileOnly("io.ktor:ktor-client-cio:2.3.13")

    // todo
    compileOnly("com.graphql-java:graphql-java:24.3")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.8.1")
}