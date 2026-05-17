package com.davinchicoder.ledgerkt.transaction.infrastructure.database

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Table(name = "transactions")
@Entity
class TransactionEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    var idempotencyKey: UUID,

    var fromAccount: UUID,
    var toAccount: UUID,

    var amount: BigDecimal,

    var fromCurrency: String,
    var toCurrency: String,
    var createdAt: Instant = Instant.now()

)
