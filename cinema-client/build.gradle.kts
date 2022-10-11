description = "Cinema Client"

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot") version "2.5.2"
}

dependencies {
    implementation(project(":cinema-api"))

    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.13.4")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("io.ktor:ktor-client-cio:2.1.2")
    implementation("io.ktor:ktor-client-websockets:2.1.2")

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}