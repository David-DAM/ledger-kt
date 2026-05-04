package com.davinchicoder.ledgerkt.transaction.infrastructure

import com.davinchicoder.ledgerkt.transaction.domain.Transaction
import com.davinchicoder.ledgerkt.transaction.infrastructure.database.TransactionEntity
import com.davinchicoder.ledgerkt.transaction.infrastructure.database.TransactionQueryRepository
import com.davinchicoder.ledgerkt.transaction.infrastructure.database.TransactionRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant
import java.util.*

class TransactionRepositoryTest {

    private val queryRepository = mockk<TransactionQueryRepository>()
    private val transactionRepository = TransactionRepository(queryRepository)

    @Test
    fun `should return transaction by idempotency key`() {
        val key = UUID.randomUUID()
        val createdAt = Instant.now()

        val expectedEntity = TransactionEntity(
            idempotencyKey = key,
            id = key,
            fromAccount = key,
            toAccount = key,
            amount = BigDecimal.TEN,
            createdAt = createdAt
        )

        val expectedDomain = Transaction(
            idempotencyKey = key,
            id = key,
            fromAccount = key,
            toAccount = key,
            amount = BigDecimal.TEN,
            createdAt = createdAt
        )

        every { queryRepository.findByIdempotencyKey(key) } returns expectedEntity

        val result = transactionRepository.getByIdempotencyKey(key)

        result shouldBe expectedDomain
        verify(exactly = 1) { queryRepository.findByIdempotencyKey(key) }
    }
}