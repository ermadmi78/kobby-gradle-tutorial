import io.github.ermadmi78.kobby.kobby

description = "Cinema API"

buildscript {
    dependencies {
        classpath("io.github.ermadmi78:kobby-gradle-plugin:5.1.1-SNAPSHOT")
    }
}

plugins {
    kotlin("jvm")
    //id("io.github.ermadmi78.kobby") version "5.0.0"
}

apply(plugin = "io.github.ermadmi78.kobby")

kotlin {
    jvmToolchain(17)
}

kobby {
    kotlin {
        entity {
            projection {
                enableNotationWithoutParentheses = true
            }
        }
    }
}

dependencies {
    // Add this dependency to enable Jackson annotation generation in DTO classes
    compileOnly("com.fasterxml.jackson.core:jackson-annotations:2.15.4")

    // Add this dependency to enable default Ktor adapters generation
    compileOnly("io.ktor:ktor-client-cio:2.3.13")
}