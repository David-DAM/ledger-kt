package com.davinchicoder.ledgerkt.transaction.infrastructure.api

import java.math.BigDecimal
import java.util.*

data class CreateTransferDto(
    val idempotencyKey: UUID,
    val fromAccountId: UUID,
    val toAccountId: UUID,
    val amount: BigDecimal,
) {
    init {
        require(amount > BigDecimal.ZERO) { "Amount must be positive" }
        require(fromAccountId != toAccountId) { "From and to account cannot be the same" }
    }
}
