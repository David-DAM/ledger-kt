package com.davinchicoder.ledgerkt.currency

import com.davinchicoder.ledgerkt.common.logger
import org.springframework.resilience.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class CurrencyRateClient(
    private val restClient: RestClient
) {

    companion object {
        private val log = logger<CurrencyRateClient>()
    }

    @Retryable
    fun getCurrencyRate(from: String, to: String = "USD"): Result<Double> = runCatching {

        val response = restClient.get()
            .uri("/latest?from=$from&to=$to")
            .retrieve()
            .onStatus({ status -> !status.is2xxSuccessful }) { request, response ->
                val statusCode = response.statusCode
                val uri = request.uri
                val body = String(response.body.readAllBytes())
                log.error("API returned error status $statusCode. Response body: $body. Request URI: $uri")
                error("Currency API error: $statusCode - $body")
            }
            .body<CurrencyRateResponse>()
            ?: error("Empty response")

        response.rates[to]
            ?: error("Currency rate missing")
    }
}