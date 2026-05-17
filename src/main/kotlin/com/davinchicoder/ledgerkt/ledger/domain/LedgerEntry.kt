package com.davinchicoder.ledgerkt.ledger.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.*


data class LedgerEntry(

    val id: UUID = UUID.randomUUID(),

    val transactionId: UUID,

    val accountId: UUID,

    val amount: BigDecimal,

    val type: EntryType,

    val currency: Currency,

    val createdAt: Instant = Instant.now()
)
