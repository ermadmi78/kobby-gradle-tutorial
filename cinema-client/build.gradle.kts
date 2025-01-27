description = "Cinema Client"

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot") version "3.2.4"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":cinema-api"))

    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.4")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.15.4")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    implementation("io.ktor:ktor-client-cio:2.3.13")
    implementation("io.ktor:ktor-client-websockets:2.3.13")

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}