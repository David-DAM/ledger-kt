package com.davinchicoder.ledgerkt.transaction.application

import java.time.Instant
import java.util.*

data class CreateTransferResponse(
    val transferId: UUID,
    val createdAt: Instant,
)
