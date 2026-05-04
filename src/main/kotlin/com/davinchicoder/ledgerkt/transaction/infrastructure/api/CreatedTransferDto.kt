package com.davinchicoder.ledgerkt.transaction.infrastructure.api

import java.time.Instant
import java.util.*

data class CreatedTransferDto(
    val id: UUID,
    val createdAt: Instant,
)
