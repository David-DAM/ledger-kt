package com.davinchicoder.ledgerkt.transaction.application

import java.math.BigDecimal
import java.util.*

data class CreateTransferRequest(
    val idempotencyKey: UUID,
    val fromAccountId: UUID,
    val toAccountId: UUID,
    val amount: BigDecimal,
    val currency: Currency,
)
