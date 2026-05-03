package com.davinchicoder.ledgerkt.transaction.application

import java.math.BigDecimal
import java.util.*

data class CreateTransferRequest(
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
