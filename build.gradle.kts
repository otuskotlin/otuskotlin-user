plugins {
    kotlin("jvm") apply false
    kotlin("multiplatform") apply false
}

group = "ru.otus.otuskotlin.user"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
        jcenter()
    }
}

