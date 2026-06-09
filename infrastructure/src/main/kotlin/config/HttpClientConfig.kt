package org.example.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.jackson.JacksonConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HttpClientConfig {
    @Bean
    open fun httpClient(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) {
                install(ContentNegotiation) {
                    register(ContentType.Application.Json, JacksonConverter())
                }
            }
        }
}
