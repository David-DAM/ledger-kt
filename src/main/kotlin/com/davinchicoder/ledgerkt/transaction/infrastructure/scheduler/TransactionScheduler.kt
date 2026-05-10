package com.davinchicoder.ledgerkt.transaction.infrastructure.scheduler

import com.davinchicoder.ledgerkt.common.logger
import com.davinchicoder.ledgerkt.transaction.domain.Transaction
import com.davinchicoder.ledgerkt.transaction.infrastructure.transactionFromCsv
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.math.BigDecimal

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
                    .map(::transactionFromCsv)
                    .also { transaction -> log.info("Transaction: $transaction") }
                    .filter(::isTransactionValid)
                    .chunked(100)
                    .forEach(::saveBatch)
            }

        log.info("Final transactions processed")
    }

    fun isTransactionValid(transaction: Transaction): Boolean {
        return transaction.amount > BigDecimal.ZERO && transaction.fromAccount != transaction.toAccount
    }

    fun saveBatch(transactions: List<Transaction>) {
        log.info("Saving batch of ${transactions.size} transactions")
    }

}