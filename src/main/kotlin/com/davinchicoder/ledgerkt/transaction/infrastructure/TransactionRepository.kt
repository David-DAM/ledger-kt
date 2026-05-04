package com.davinchicoder.ledgerkt.transaction.infrastructure

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TransactionRepository(
    private val repository: TransactionQueryRepository
) {
    fun getByIdempotencyKey(idempotencyKey: UUID) = repository.findByIdempotencyKey(idempotencyKey)
    fun getAll() = repository.findAll()
    fun save(entry: TransactionEntity) = repository.save(entry)

}