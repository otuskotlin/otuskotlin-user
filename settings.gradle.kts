rootProject.name = "otuskotlin-user"

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openapiVersion: String by settings
        val springVersion: String by settings
        val springDependencyVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
        kotlin("js") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version "4.3.1" apply false
        id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
        id("org.springframework.boot") version springVersion
        id("io.spring.dependency-management") version springDependencyVersion

    }

    repositories {
        maven { url = uri("https://repo.spring.io/snapshot") }
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

include("ok-user-mp-common")
include("ok-user-mp-transport-models")

include("ok-user-be-common")
include("ok-user-be-transport-multiplatform")
include("ok-user-be-app-jetty")
include("ok-user-fe-transport-multiplatform")
include("ok-user-fe-angular")
include("ok-user-oa-transport-models-jvm")
