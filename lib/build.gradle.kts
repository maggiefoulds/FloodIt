plugins {
    kotlin("jvm")
    `java-library`
    id("org.jetbrains.dokka") version "1.7.20"
}

dependencies {

    api("io.github.pdvrieze.matrixlib:matrixlib:1.0.1")

    testImplementation("org.junit.jupiter:junit-jupiter:${rootProject.extra["junit_version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${rootProject.extra["junit_version"]}")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
