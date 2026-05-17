package com.davinchicoder.ledgerkt.currency

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient

@Configuration
class CurrencyClientConfig(
    @Value($$"${app.client.baseUrl}")
    private val baseUrl: String
) {

    @Bean
    fun restClient(): RestClient =
        RestClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader("User-Agent", "LedgerKt/1.0")
            .build()
}