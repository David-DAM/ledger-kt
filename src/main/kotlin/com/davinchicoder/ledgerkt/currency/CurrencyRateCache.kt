package com.davinchicoder.ledgerkt.currency

import com.davinchicoder.ledgerkt.common.logger
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class CurrencyRateCache(
    private val client: CurrencyRateClient,
    private val cache: MutableMap<String, Double> = ConcurrentHashMap()
) {
    companion object {
        private val log = logger<CurrencyRateCache>()
    }

    fun get(currency: String): Double {
        if (cache.containsKey(currency)) {
            return cache[currency]!!
        }

        val result = client.getCurrencyRate(currency)

        val currencyRate = result
            .onSuccess {
                log.info("$currency rate: $it")
                cache[currency] = it
            }
            .onFailure {
                log.error("Error fetching currency rate: ${it.message}")
            }

        return currencyRate.getOrThrow()
    }

    fun set(currency: String, rate: Double) {
        cache[currency] = rate
    }
}