plugins {
    kotlin("multiplatform")
}

group = rootProject.group
version = rootProject.version

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    js {
        browser()
//        nodejs()
    }
    jvm()

    sourceSets {
        val coroutinesVersion: String by project
        val serializationVersion: String by project

        val commonMain by getting {
            dependencies {

                implementation(kotlin("stdlib-common"))
                implementation(project(":ok-user-mp-transport-models"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion")
                if (serializationVersion.startsWith("0.")) {
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion")
                } else {
                    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
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
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$coroutinesVersion")
                    if (serializationVersion.startsWith("0.")) {
                        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serializationVersion")
                    }
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
                    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                    if (serializationVersion.startsWith("0.")) {
                        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
                    }
                }
            }
            val jvmTest by getting {
                dependencies {
                    implementation(kotlin("test"))
                    implementation(kotlin("test-junit"))
                }
            }
        }
    }
}
