plugins {
    kotlin("jvm")
    kotlin("plugin.spring") version "2.4.0"
}

group = "org.example"
version = "unspecified"

dependencies {
    implementation(project(":domain"))
    implementation(project(":infrastructure"))

    implementation("org.springframework:spring-tx:6.1.13")
    implementation("org.springframework:spring-context:6.1.13")
    implementation("org.springframework.boot:spring-boot-autoconfigure:3.3.4")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

    implementation("org.slf4j:slf4j-api:2.0.9")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}