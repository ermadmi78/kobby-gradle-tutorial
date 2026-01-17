import org.springframework.boot.gradle.plugin.SpringBootPlugin

description = "Cinema Server"

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot") version "3.5.9"
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":cinema-api"))

    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.data:spring-data-commons")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.1")
}