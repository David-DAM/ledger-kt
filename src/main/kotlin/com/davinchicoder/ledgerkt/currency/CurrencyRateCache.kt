package com.davinchicoder.ledgerkt.currency

import com.davinchicoder.ledgerkt.common.logger
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class CurrencyRateCache(
    private val client: CurrencyRateClient,
    private val cache: MutableMap<String, MutableMap<String, Double>> = ConcurrentHashMap()
) {
    companion object {
        private val log = logger<CurrencyRateCache>()
    }

    fun get(from: String, to: String): Double {

        if (from == to) return 1.0

        if (cache.containsKey(from) && cache[from]!!.containsKey(to)) {
            return cache[from]!![to]!!
        }

        val result = client.getCurrencyRate(from, to)

        val currencyRate = result
            .onSuccess {
                log.info("$from to $to rate: $it")
                cache[from] = mutableMapOf(to to it)
            }
            .onFailure {
                log.error("Error fetching currency rate from $from to $to: ${it.message}")
            }

        return currencyRate.getOrThrow()
    }

    fun set(from: String, to: String, rate: Double) {
        if (cache.containsKey(from)) {
            cache[from]!![to] = rate
            return
        }
        cache[from] = mutableMapOf(to to rate)
    }
}