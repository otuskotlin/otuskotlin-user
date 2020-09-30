rootProject.name = "otuskotlin-user"

val serializationVersion: String by settings

pluginManagement {
    plugins {
        val kotlinVersion: String by settings
        val openapiVersion: String by settings
        val springVersion: String by settings
        val springDependencyVersion: String by settings
        val bmuschkoVersion: String by settings
        val kotlessVersion: String by settings

        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("jvm") version kotlinVersion apply false
        kotlin("js") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
        id("org.openapi.generator") version "4.3.1" apply false
        id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion apply false
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion apply false
        id("org.springframework.boot") version springVersion apply false
        id("io.spring.dependency-management") version springDependencyVersion apply false
        id("com.bmuschko.docker-java-application") version bmuschkoVersion apply false
        id("io.kotless") version kotlessVersion apply false
    }

    repositories {
        maven { url = uri("https://repo.spring.io/snapshot") }
        maven { url = uri("https://repo.spring.io/milestone") }
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}
//enableFeaturePreview("GRADLE_METADATA")

include(":ok-user-mp-common")
include(":ok-user-mp-transport-models")

include(":ok-user-be-common")
include(":ok-user-be-transport-multiplatform")
include(":ok-user-fe-transport-multiplatform")
//include("ok-user-fe-angular")
//include("ok-user-oa-transport-models-jvm")

include(":ok-user-be-app-jetty")
if (serializationVersion.startsWith("0.")) {
    include(":ok-user-be-app-kotless")
} else {
    include(":ok-user-be-app-ktor")
}
include("ok-user-be-logics")
