rootProject.name = "otuskotlin-user"
include("ok-user-be-common")

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
    }
}
