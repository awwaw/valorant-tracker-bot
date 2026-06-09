plugins {
    kotlin("jvm")
}

group = "org.example"
version = "unspecified"

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}