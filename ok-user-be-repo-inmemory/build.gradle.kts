plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {

    val coroutinesVersion: String by project
    val cache2kVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":ok-user-be-common"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.cache2k:cache2k-core:$cache2kVersion")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
