plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
