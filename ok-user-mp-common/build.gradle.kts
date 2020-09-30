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
        nodejs()
    }
    jvm {
        withJava()
    }
//    linuxX64()

    sourceSets {
        val coroutinesVersion: String by project
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$coroutinesVersion")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
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
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(npm("is-sorted", "1.0.5"))
                implementation(npm("mathjs", "7.2.0"))
                implementation(kotlin("test"))
                implementation(kotlin("test-js"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

//        val linuxX64Main by getting {
//            dependencies {
//                implementation(kotlin("stdlib"))
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$coroutinesVersion")
//            }
//        }
//        val linuxX64Test by getting {
//            dependencies {
//                implementation(kotlin("test"))
//            }
//        }
    }
}
