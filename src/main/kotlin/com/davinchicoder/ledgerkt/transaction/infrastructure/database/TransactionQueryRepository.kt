package com.davinchicoder.ledgerkt.transaction.infrastructure.database

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransactionQueryRepository : JpaRepository<TransactionEntity, UUID> {

    fun findByIdempotencyKey(idempotencyKey: UUID): TransactionEntity?

}