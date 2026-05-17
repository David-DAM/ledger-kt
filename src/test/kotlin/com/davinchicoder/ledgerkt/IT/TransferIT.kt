package com.davinchicoder.ledgerkt.IT

import com.davinchicoder.ledgerkt.transaction.application.CreateTransferRequest
import com.davinchicoder.ledgerkt.transaction.infrastructure.database.TransactionRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.client.RestTestClient
import java.math.BigDecimal
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class TransferIT {

    @Autowired
    lateinit var client: RestTestClient

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @Sql(value = ["/it/success/data.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = ["/it/clean.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    fun `should create transfer`() {

        val request = CreateTransferRequest(
            idempotencyKey = UUID.randomUUID(),
            fromAccountId = UUID.fromString("3fa004db-e4c5-457c-901f-3e36ee651f56"),
            toAccountId = UUID.fromString("3fa004db-e4c5-457c-901f-3e36ee651f57"),
            amount = BigDecimal.TEN,
            fromCurrency = Currency.getInstance("EUR"),
            toCurrency = Currency.getInstance("USD"),
        )

        client.post()
            .uri("/api/v1/ledger/transfer")
            .body(request)
            .exchange()
            .expectStatus()
            .isOk
            .expectBody()
            .jsonPath("$.id").exists()
            .jsonPath("$.createdAt").exists()

        assertTrue(transactionRepository.getAll().isNotEmpty())
    }
}