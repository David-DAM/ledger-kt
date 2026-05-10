package com.davinchicoder.ledgerkt.transaction.infrastructure.scheduler

import com.davinchicoder.ledgerkt.common.logger
import com.davinchicoder.ledgerkt.transaction.infrastructure.transactionFromCsv
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File

@Profile("!test")
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
                        val transaction = transactionFromCsv(parts)
                        log.info("Transaction: $transaction")
                    }
            }

        log.info("Final transactions processed")
    }

}