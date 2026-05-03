package com.davinchicoder.ledgerkt.IT

import com.davinchicoder.ledgerkt.transaction.application.CreateTransferRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.client.RestTestClient
import java.math.BigDecimal
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class TransferIT {

    @Autowired
    lateinit var client: RestTestClient

    @Test
    fun `should create transfer via http`() {

        val request = CreateTransferRequest(
            idempotencyKey = UUID.randomUUID(),
            fromAccountId = UUID.randomUUID(),
            toAccountId = UUID.randomUUID(),
            amount = BigDecimal.TEN
        )

        client.post()
            .uri("/api/ledger/transfer")
            .body(request)
            .exchange()
            .expectStatus()
            .isOk

    }
}