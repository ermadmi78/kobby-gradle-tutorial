import org.springframework.boot.gradle.plugin.SpringBootPlugin

description = "Cinema Server"

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

    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:15.0.0")

    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.7.3")
}