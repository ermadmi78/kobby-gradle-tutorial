description = "Cinema Server"

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot") version "2.5.2"
}

dependencies {
    implementation(project(":cinema-api"))

    implementation(
        "com.graphql-java-kickstart:" +
                "graphql-kickstart-spring-boot-starter-webflux:11.1.0"
    )
    implementation(
        "com.graphql-java-kickstart:" +
                "graphql-kickstart-spring-boot-starter-tools:11.1.0"
    )
    implementation(
        "com.graphql-java-kickstart:" +
                "graphiql-spring-boot-starter:11.1.0"
    )
    implementation(
        "com.graphql-java-kickstart:" +
                "playground-spring-boot-starter:11.1.0"
    )

    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.6.4")
}