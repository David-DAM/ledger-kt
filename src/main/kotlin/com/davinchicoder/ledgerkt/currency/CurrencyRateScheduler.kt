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

        val currencies = setOf(
            "EUR", "GBP", "USD", "JPY",
        )

        currencies.map { fromCurrency ->
            async(Dispatchers.IO) {
                currencies.filter { it != fromCurrency }
                    .forEach { toCurrency ->
                        val currencyRate = currencyRateClient.getCurrencyRate(fromCurrency, toCurrency)
                        currencyRate.onSuccess {
                            log.info("Exchange for $fromCurrency to $toCurrency is $it")
                            currencyCache.set(fromCurrency, toCurrency, it)
                        }.onFailure {
                            log.error("Error fetching currency rate: ${it.message}")
                        }
                    }
            }
        }.awaitAll()
        log.info("Currency exchanges complete")
    }

}