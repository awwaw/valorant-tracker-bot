plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version "2.4.0"
    kotlin("plugin.spring") version "2.4.0"
    id("org.jooq.jooq-codegen-gradle") version "3.19.10"
}

group = "org.example"
version = "unspecified"

dependencies {
    implementation(project(":domain"))

    implementation("org.flywaydb:flyway-core:10.15.0")
    implementation("org.flywaydb:flyway-database-postgresql:10.15.0")
    implementation("org.jooq:jooq:3.19.10")
    implementation("org.postgresql:postgresql:42.7.3")

    implementation("io.ktor:ktor-client-core:2.3.9")
    implementation("io.ktor:ktor-client-cio:2.3.9")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.9")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")
    implementation("io.ktor:ktor-serialization-jackson:2.3.9")

    implementation("org.springframework:spring-context:6.1.13")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:4.1.3")

    jooqCodegen("org.postgresql:postgresql:42.7.3")

    testImplementation(kotlin("test"))
}

jooq {
    configuration {
        jdbc {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/valorant_tracker"
            user = "tracker"
            password = "tracker"
        }
        generator {
            name = "org.jooq.codegen.KotlinGenerator"
            database {
                name = "org.jooq.meta.postgres.PostgresDatabase"
                inputSchema = "public"
            }
            generate {
                isSpringAnnotations = false
                isJpaAnnotations = false
            }
            target {
                packageName = "generated.db"
                directory = "src/main/kotlin/db"
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
