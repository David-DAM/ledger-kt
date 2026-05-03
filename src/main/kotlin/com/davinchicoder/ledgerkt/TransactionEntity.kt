package com.davinchicoder.ledgerkt

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
data class Transaction(
    @Id
    val id: UUID = UUID.randomUUID(),

    val idempotencyKey: String,

    val fromAccount: UUID,
    val toAccount: UUID,

    val amount: BigDecimal,

    val createdAt: Instant = Instant.now()
)
