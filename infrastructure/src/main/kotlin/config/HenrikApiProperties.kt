package org.example.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "henrik")
data class HenrikApiProperties(
    val baseUrl: String,
    val apiKey: String,
)
