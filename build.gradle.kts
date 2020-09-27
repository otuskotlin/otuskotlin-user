plugins {
    kotlin("js") apply false
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
    kotlin("plugin.serialization") apply false
    id("com.bmuschko.docker-java-application") apply false
    id("io.kotless") apply false
}

group = "ru.otus.otuskotlin.user"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven { url = uri("https://kotlin.bintray.com/ktor") }
    }
}

