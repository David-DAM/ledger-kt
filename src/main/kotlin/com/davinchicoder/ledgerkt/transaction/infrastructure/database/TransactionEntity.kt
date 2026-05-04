package com.davinchicoder.ledgerkt.transaction.infrastructure.database

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Table(name = "transactions")
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
