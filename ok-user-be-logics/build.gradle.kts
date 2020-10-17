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

    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":ok-user-mp-common"))
    implementation(project(":ok-user-be-common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation(project(":ok-user-be-repo-inmemory"))
}
