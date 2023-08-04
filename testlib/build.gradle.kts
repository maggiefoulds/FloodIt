import org.jetbrains.kotlin.gradle.plugin.extraProperties

plugins {
    kotlin("jvm")
    this.
    kotlin("plugin.serialization") version "1.8.0"
}

dependencies {
    api(project(":lib"))
    implementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junit_version"]}")
    implementation("org.junit.jupiter:junit-jupiter-params:${rootProject.extra["junit_version"]}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}

repositories {
    mavenCentral()
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
