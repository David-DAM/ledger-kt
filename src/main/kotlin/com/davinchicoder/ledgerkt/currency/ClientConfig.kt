package com.davinchicoder.ledgerkt.currency

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class ClientConfig {

    @Bean
    fun restClient(): RestClient =
        RestClient.builder()
            .baseUrl("https://api.frankfurter.app")
            .build()
}