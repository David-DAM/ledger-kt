package com.davinchicoder.ledgerkt.currency

data class FxResponse(
    val amount: Double,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
