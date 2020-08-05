plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val coroutinesVersion: String by project

    implementation(project(":ok-user-common"))
    implementation(project(":ok-user-transport-multiplatform"))

    implementation(kotlin("stdlib"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
