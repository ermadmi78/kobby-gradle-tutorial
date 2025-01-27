import org.springframework.boot.gradle.plugin.SpringBootPlugin

description = "Cinema Server"

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

    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.data:spring-data-commons")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.4")

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.8.1")
}