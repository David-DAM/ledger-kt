package com.davinchicoder.ledgerkt.transaction.infrastructure.database

import com.davinchicoder.ledgerkt.transaction.domain.Transaction
import com.davinchicoder.ledgerkt.transaction.infrastructure.toDomain
import com.davinchicoder.ledgerkt.transaction.infrastructure.toEntity
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TransactionRepository(
    private val repository: TransactionQueryRepository
) {
    fun getByIdempotencyKey(idempotencyKey: UUID) = repository.findByIdempotencyKey(idempotencyKey)?.toDomain()

    fun getAll() = repository.findAll().map { it.toDomain() }
    fun save(entry: Transaction) = repository.save(entry.toEntity()).toDomain()

}