package com.davinchicoder.ledgerkt.transaction.application

import com.davinchicoder.ledgerkt.transaction.domain.Transaction
import java.time.Instant
import java.util.*

data class CreateTransferResponse(
    val id: UUID,
    val createdAt: Instant,
)

fun Transaction.toCreateTransferResponse(): CreateTransferResponse = CreateTransferResponse(id, createdAt)
