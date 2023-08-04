plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":lib"))
    testImplementation(project(":testlib"))

    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${rootProject.extra["junit_version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${rootProject.extra["junit_version"]}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${rootProject.extra["junit_version"]}")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
