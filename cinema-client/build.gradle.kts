description = "Cinema Client"

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot") version "3.1.5"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":cinema-api"))

    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.15.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation("io.ktor:ktor-client-cio:2.3.6")
    implementation("io.ktor:ktor-client-websockets:2.3.6")

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}