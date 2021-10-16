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

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.2")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.12.2")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")

    implementation("io.ktor:ktor-client-cio:1.5.4")
    implementation("io.ktor:ktor-client-jackson:1.5.4")
    implementation("io.ktor:ktor-client-websockets:1.5.4")

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}