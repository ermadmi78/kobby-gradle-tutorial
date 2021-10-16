description = "Kobby Plugin Gradle Tutorial"

plugins {
    kotlin("jvm") apply false
}

allprojects {
    buildscript {
        repositories {
            mavenLocal()
            mavenCentral()
        }
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }
}