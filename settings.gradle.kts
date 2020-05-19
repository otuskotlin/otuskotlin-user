rootProject.name = "otuskotlin-202007-user"
include("otuskotlin-202007-users-common")

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
    }
}