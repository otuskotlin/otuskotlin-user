rootProject.name = "otuskotlin-user"
include("ok-user-common")
include("ok-user-transport-common")
include("ok-user-transport-multiplatform")

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
    }
}
