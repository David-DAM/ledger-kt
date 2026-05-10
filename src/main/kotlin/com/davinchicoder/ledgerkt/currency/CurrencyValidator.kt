package com.davinchicoder.ledgerkt.currency

import com.davinchicoder.ledgerkt.common.logger
import org.springframework.stereotype.Service

@Service
class CurrencyValidator(
    private val fxService: FxService
) {
    companion object {
        private val log = logger<CurrencyValidator>()
    }

    fun validateCurrency(currency: String): Double {
        val result = fxService.getCurrencyRate(currency)

        val currencyRate = result
            .onSuccess {
                log.info("$currency rate: $it")
            }
            .onFailure {
                log.error("Error fetching currency rate: ${it.message}")
            }
        
        return currencyRate.getOrThrow()
    }
}