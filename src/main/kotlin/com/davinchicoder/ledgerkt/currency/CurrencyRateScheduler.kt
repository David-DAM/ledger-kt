package com.davinchicoder.ledgerkt.currency

import com.davinchicoder.ledgerkt.common.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Profile("!test")
@Component
class CurrencyRateScheduler(
    private val currencyRateClient: CurrencyRateClient,
    private val currencyCache: CurrencyRateCache,
) {
    companion object {
        private val log = logger<CurrencyRateScheduler>()
    }

    @Scheduled(cron = "0 * * * * *")
    fun currencyRate() = runBlocking {
        log.info("Currency exchanges started")

        setOf(
            "EUR", "GBP", "USD", "CAD", "AUD", "NZD", "JPY",
        ).map { currency ->
            async(Dispatchers.IO) {
                val currencyRate = currencyRateClient.getCurrencyRate(currency)
                currencyRate.onSuccess {
                    log.info("Exchange for $currency is $it")
                    currencyCache.set(currency, it)
                }.onFailure {
                    log.error("Error fetching currency rate: ${it.message}")
                }
            }
        }.awaitAll()
        log.info("Currency exchanges complete")
    }

}