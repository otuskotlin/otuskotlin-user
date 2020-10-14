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
    val testContainersVersion: String by project
    val cassandraVersion: String by project

    implementation(kotlin("stdlib"))

    implementation(project(":ok-user-be-common"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:$coroutinesVersion")

    implementation("com.datastax.cassandra:cassandra-driver-core:$cassandraVersion")
    implementation("com.datastax.cassandra:cassandra-driver-mapping:$cassandraVersion")
    implementation("com.datastax.cassandra:cassandra-driver-extras:$cassandraVersion")


    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
}
