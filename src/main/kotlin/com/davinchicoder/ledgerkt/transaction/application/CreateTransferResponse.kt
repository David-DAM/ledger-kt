package com.davinchicoder.ledgerkt.transaction.application

import java.time.Instant
import java.util.*

data class CreateTransferResponse(
    val id: UUID,
    val createdAt: Instant,
)
