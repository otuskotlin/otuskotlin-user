import io.kotless.plugin.gradle.dsl.kotless
import io.kotless.plugin.gradle.dsl.Webapp.Route53

plugins {
    kotlin("jvm")
    id("io.kotless") apply true
    kotlin("plugin.serialization")
}

group = rootProject.group
version = rootProject.version

kotless {
    config {
        bucket = "com.crowdproj.user"
        prefix = "user"

        terraform {
//            profile = "kotless-jetbrains"
            profile = "default"
            region = "us-east-1"
        }
        optimization {
            mergeLambda = io.kotless.KotlessConfig.Optimization.MergeLambda.All
//            autowarm = io.kotless.plugin.gradle.dsl.KotlessConfig.Optimization.Autowarm(enable = true, minutes = 5)

        }
    }

    webapp {
        route53 = Route53("user", "crowdproj.com","crowdproj.com")
    }

    extensions {

        local {
            useAWSEmulation = true
        }

        terraform {
            allowDestroy = true
            files {
                add(file("src/main/tf/dynamodb.tf"))
                add(file("src/main/tf/dynamodb-access.tf"))
            }
        }
    }

}

dependencies {
    val kotlessVersion: String by project
    val coroutinesVersion: String by project

    implementation(project(":ok-user-be-transport-multiplatform"))
    implementation(project(":ok-user-be-logics"))
    implementation(project(":ok-user-be-repo-inmemory"))
    implementation(project(":ok-user-be-repo-dynamodb"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("io.kotless", "kotless-lang", kotlessVersion)
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
}

tasks {
    withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java) {
        targetCompatibility = "1.8"
        kotlinOptions {
            jvmTarget = "1.8"
            apiVersion = "1.3"
            languageVersion = "1.3"
        }
    }
}
