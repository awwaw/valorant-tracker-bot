package org.example.client

import feign.RequestInterceptor
import feign.RequestTemplate
import org.example.config.HenrikApiProperties
import org.springframework.stereotype.Component

@Component
class HenrikApiInterceptor(
    private val properties: HenrikApiProperties,
) : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        template.header("Authorization", properties.apiKey)
    }
}
