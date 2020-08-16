plugins {
  id("com.crowdproj.plugins.jar2npm") version "2.0.0"
}

group = rootProject.group
version = rootProject.version

node {
  download = true
  version = "14.8.0"
}

tasks {
  val ngInitLibs by creating(com.moowork.gradle.node.npm.NpxTask::class) {
    dependsOn(yarnSetup)
    command = "npm"
    setArgs(listOf(
            "install",
            "@angular/cli"
    ))
  }
  val ngInit by creating(com.moowork.gradle.node.npm.NpxTask::class) {
    dependsOn(ngInitLibs)
    command = "ng"
    setArgs(listOf(
            "new",
            "ok-user-fe-angular",
            "--directory",
            "./"
    ))
  }

  val ngBuild by creating(com.moowork.gradle.node.npm.NpxTask::class) {
    dependsOn(yarnSetup)
    command = "ng"
    setArgs(listOf(
      "build"
    ))
  }

  val ngStart by creating(com.moowork.gradle.node.npm.NpxTask::class) {
    dependsOn(yarnSetup)
    command = "ng"
    setArgs(listOf(
      "serve"
    ))
  }
}
