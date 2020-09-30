val ktorVersion: String by project
val logbackVersion: String by project

plugins {
    application
    kotlin("jvm")
    id("com.bmuschko.docker-java-application")
}

group = rootProject.group
version = rootProject.version

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

docker {
//  url = 'https://192.168.59.103:2376'
//  certPath = new File(System.properties['user.home'], '.boot2docker/certs/boot2docker-vm')

//    registryCredentials {
//        url.set(dockerParams.dockerUrl)
//        dockerParams.dockerUser?.also { username.set(it) }
//        dockerParams.dockerPass?.also { password.set(it) }
//    email = 'benjamin.muschko@gmail.com'
//    }

    javaApplication {
        baseImage.set("adoptopenjdk/openjdk11:alpine-jre")
        maintainer.set("(c) Otus")
        ports.set(listOf(8080))
        val imageName = project.name
        images.set(listOf(
                "$imageName:${project.version}",
                "$imageName:latest"
        ))
        jvmArgs.set(listOf("-Xms256m", "-Xmx512m"))
    }
}


dependencies {
    implementation(project(":ok-user-be-common"))
    implementation(project(":ok-user-mp-transport-models"))
    implementation(project(":ok-user-be-transport-multiplatform"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
