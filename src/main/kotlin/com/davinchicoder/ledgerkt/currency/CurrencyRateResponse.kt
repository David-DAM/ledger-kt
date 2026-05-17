package com.davinchicoder.ledgerkt.currency

data class CurrencyRateResponse(
    val amount: Double,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
