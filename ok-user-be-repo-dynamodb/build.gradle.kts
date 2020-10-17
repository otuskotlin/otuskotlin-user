plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    val kotlessVersion: String by project
    val coroutinesVersion: String by project
    val dynamodbVersion: String by project

    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":ok-user-be-common"))
    implementation("io.kotless", "kotless-lang", kotlessVersion)
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.amazonaws:aws-java-sdk-dynamodb:$dynamodbVersion")

}
