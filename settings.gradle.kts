rootProject.name = "otuskotlin-202007-USER"
include("users-common")

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
    }
}