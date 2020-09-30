plugins {
    kotlin("jvm")
    id("org.openapi.generator")
    id("org.jetbrains.kotlin.plugin.jpa")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
}

val spec = "$rootDir/ok-user-openapi-spec-v1.yaml"
val generatedSourcesDir = "$buildDir/generated/openapi"
val basePackage = "${rootProject.group}.transport.openapi"

openApiGenerate {
//    generatorName.set("kotlin-server")
    generatorName.set("kotlin-spring")

    inputSpec.set(spec)
    outputDir.set(generatedSourcesDir)

    apiPackage.set("$basePackage.api")
    invokerPackage.set("$basePackage.invoker")
    modelPackage.set("$basePackage.model")

    configOptions.set(mapOf(
            "dateLibrary" to "string",
            "enumPropertyNaming" to "UPPERCASE",
            "swaggerAnnotations" to "true",
            "serviceInterface" to "true"
//            "serviceImplementation" to "true"
    ))
}

springBoot {
    mainClassName = "$basePackage.invoker.ApplicationKt"
}

sourceSets {
    getByName("main") {
        java {
            srcDir("$generatedSourcesDir/src/main/kotlin")
        }
    }
}

dependencies {
    val kotlinxCoroutinesVersion: String by project
    val validationVersion: String by project
    val swaggerVersion: String by project

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("javax.validation:validation-api:$validationVersion")
    implementation("io.swagger:swagger-annotations:$swaggerVersion")
    implementation("org.springdoc:springdoc-openapi-ui:1.4.4")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }

}

repositories {
    mavenCentral()
    jcenter()
    maven { url = uri("https://repo1.maven.org/maven2") }
    maven { url = uri("https://repo.spring.io/snapshot") }
    maven { url = uri("https://repo.spring.io/milestone") }
}

tasks {
    val openApiGenerate by getting

    val compileKotlin by getting {
        dependsOn(openApiGenerate)

    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
//        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.jvmTarget = "11"
    }
}
