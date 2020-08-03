plugins {
    kotlin("jvm") version "1.3.72"
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
