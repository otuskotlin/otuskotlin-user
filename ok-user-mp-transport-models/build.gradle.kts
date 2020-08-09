plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    js {
        browser()
        nodejs()
    }
    jvm()
    linuxX64()

    sourceSets {
        val serializationVersion: String by project
        val coroutinesVersion: String by project

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion")

                implementation(project(":ok-user-mp-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serializationVersion")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-js"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
//                implementation()
            }
        }

        val linuxX64Main by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:$serializationVersion")
            }
        }
        val linuxX64Test by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
