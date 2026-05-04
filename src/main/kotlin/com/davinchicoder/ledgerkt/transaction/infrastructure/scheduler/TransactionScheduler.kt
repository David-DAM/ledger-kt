package com.davinchicoder.ledgerkt.transaction.infrastructure.scheduler

import com.davinchicoder.ledgerkt.common.logger
import com.davinchicoder.ledgerkt.transaction.domain.Transaction
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.time.Instant
import java.util.*

@Component
class TransactionScheduler {
    companion object {
        private val log = logger<TransactionScheduler>()
    }

    @Scheduled(cron = "* * 0 * * *")
    fun readTransaction() {
        log.info("Reading transaction")

        File("src/main/resources/transactions.csv")
            .useLines { lines ->
                lines
                    .drop(1)
                    .map { line ->
                        val parts = line.split(",")
                        Transaction(
                            fromAccount = UUID.fromString(parts[0]),
                            toAccount = UUID.fromString(parts[1]),
                            amount = parts[2].toBigDecimal(),
                            id = UUID.randomUUID(),
                            idempotencyKey = UUID.randomUUID(),
                            createdAt = Instant.now()
                        )
                        log.info("Transaction: $line")
                    }
            }

        log.info("Final transactions processed")
    }

}