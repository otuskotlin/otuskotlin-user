rootProject.name = "otuskotlin-user"
include("ok-user-be-common")
include("ok-user-mp-transport-models")

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
    }
}
