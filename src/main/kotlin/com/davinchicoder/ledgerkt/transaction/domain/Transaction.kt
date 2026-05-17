package com.davinchicoder.ledgerkt.transaction.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class Transaction(

    val id: UUID = UUID.randomUUID(),

    val idempotencyKey: UUID,

    val fromAccount: UUID,
    val toAccount: UUID,

    val amount: BigDecimal,

    val createdAt: Instant = Instant.now(),

    val currency: Currency
)