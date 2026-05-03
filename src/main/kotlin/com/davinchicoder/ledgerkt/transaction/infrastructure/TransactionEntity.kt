package com.davinchicoder.ledgerkt.transaction.infrastructure

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
data class TransactionEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    val idempotencyKey: UUID,

    val fromAccount: UUID,
    val toAccount: UUID,

    val amount: BigDecimal,

    val createdAt: Instant = Instant.now()
)
