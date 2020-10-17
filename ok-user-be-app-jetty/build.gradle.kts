plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = rootProject.group
version = rootProject.version

application {
    mainClassName = "ru.otus.otuskotlin.user.backend.app.jetty.JettyMainKt"
}

dependencies {

    val coroutinesVersion: String by project
    val serializationVersion: String by project

    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":ok-user-mp-transport-models"))
    implementation(project(":ok-user-be-common"))
    implementation(project(":ok-user-be-transport-multiplatform"))

    implementation("org.glassfish.jersey.containers:jersey-container-jetty-http:2.31")
    implementation("org.glassfish.jersey.inject:jersey-hk2:2.31")
    implementation("org.glassfish.jersey.core:jersey-common:2.31")
    implementation("org.glassfish.jaxb:jaxb-runtime:2.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:2.23.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.5.5-2")

//    testImplementation("org.spockframework:spock-core:1.0-groovy-2.4")
    testImplementation("org.jetbrains.spek:spek-api:1.1.5")


    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}
