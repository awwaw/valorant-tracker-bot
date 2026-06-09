plugins {
    kotlin("jvm")
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
                packageName = "org.example.infrastructure.db"
                directory = "src/main/kotlin/db"
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}