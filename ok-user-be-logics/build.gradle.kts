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

    implementation(kotlin("stdlib"))

    implementation(project(":ok-user-mp-common"))
    implementation(project(":ok-user-be-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))
}
