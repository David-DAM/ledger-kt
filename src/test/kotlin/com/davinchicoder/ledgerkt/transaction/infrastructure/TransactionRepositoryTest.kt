package com.davinchicoder.ledgerkt.transaction.infrastructure

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

        val expected = Optional.of(
            TransactionEntity(
                idempotencyKey = key,
                id = UUID.randomUUID(),
                fromAccount = UUID.randomUUID(),
                toAccount = UUID.randomUUID(),
                amount = BigDecimal.TEN,
                createdAt = Instant.now()
            )
        )

        every { queryRepository.findByIdempotencyKey(key) } returns expected

        val result = transactionRepository.getByIdempotencyKey(key)

        result shouldBe expected
        verify(exactly = 1) { queryRepository.findByIdempotencyKey(key) }
    }
}