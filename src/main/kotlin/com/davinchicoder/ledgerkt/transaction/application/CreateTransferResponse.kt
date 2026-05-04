package com.davinchicoder.ledgerkt.transaction.application

import com.davinchicoder.ledgerkt.transaction.infrastructure.TransactionEntity
import java.time.Instant
import java.util.*

data class CreateTransferResponse(
    val id: UUID,
    val createdAt: Instant,
)

fun TransactionEntity.toCreateTransferResponse(): CreateTransferResponse =
    CreateTransferResponse(
        id = this.id,
        createdAt = this.createdAt
    )
