rootProject.name = "otuskotlin-user"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
        kotlin("js") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
    }
}

include("ok-user-mp-common")
include("ok-user-mp-transport-models")

include("ok-user-be-common")
include("ok-user-be-transport-multiplatform")
include("ok-user-be-app-jetty")
include("ok-user-fe-transport-multiplatform")
include("ok-user-fe-angular")
