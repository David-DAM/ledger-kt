package com.davinchicoder.ledgerkt.currency

import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class FxService(
    private val restClient: RestClient
) {

    fun getCurrencyRate(currency: String): Result<Double> = runCatching {

        val response = restClient.get()
            .uri("/latest?from=EUR&to=$currency")
            .retrieve()
            .body<FxResponse>()
            ?: error("Empty response")

        response.rates[currency]
            ?: error("Currency rate missing")
    }
}