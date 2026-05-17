package com.davinchicoder.ledgerkt.transaction.application

import com.davinchicoder.ledgerkt.account.infrastructure.AccountRepository
import com.davinchicoder.ledgerkt.common.logger
import com.davinchicoder.ledgerkt.currency.CurrencyRateCache
import com.davinchicoder.ledgerkt.ledger.domain.EntryType
import com.davinchicoder.ledgerkt.ledger.domain.LedgerEntry
import com.davinchicoder.ledgerkt.ledger.infrastructure.LedgerEntryRepository
import com.davinchicoder.ledgerkt.transaction.domain.Transaction
import com.davinchicoder.ledgerkt.transaction.infrastructure.database.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CreateTransferUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val ledgerRepository: LedgerEntryRepository,
    private val currencyRateCache: CurrencyRateCache,
) {

    companion object {
        private val log = logger<CreateTransferUseCase>()
    }

    @Transactional
    fun handle(request: CreateTransferRequest): CreateTransferResponse {
        log.info("Creating transfer for ${request.fromAccountId} to ${request.toAccountId}")
        val existingTransaction = transactionRepository.getByIdempotencyKey(request.idempotencyKey)

        existingTransaction?.let {
            log.info("Transaction already exists")
            return it.toCreateTransferResponse()
        }

        val fromAccount = accountRepository.getAccount(request.fromAccountId)
            ?: throw IllegalArgumentException("From account not found")

        val toAccount = accountRepository.getAccount(request.toAccountId)
            ?: throw IllegalArgumentException("To account not found")

        val rate = currencyRateCache.get(request.fromCurrency.currencyCode, request.toCurrency.currencyCode)

        val convertedAmount = request.amount.multiply(
            BigDecimal.valueOf(rate)
        )

        val transaction = Transaction(
            idempotencyKey = request.idempotencyKey,
            fromAccount = fromAccount.id,
            toAccount = toAccount.id,
            amount = convertedAmount,
            fromCurrency = request.fromCurrency,
            toCurrency = request.toCurrency,
        )

        val saved = transactionRepository.save(transaction)

        ledgerRepository.saveAll(
            listOf(
                LedgerEntry(
                    amount = request.amount,
                    type = EntryType.DEBIT,
                    accountId = fromAccount.id,
                    transactionId = saved.id,
                    currency = request.fromCurrency
                ),
                LedgerEntry(
                    amount = convertedAmount,
                    type = EntryType.CREDIT,
                    accountId = toAccount.id,
                    transactionId = saved.id,
                    currency = request.toCurrency
                )
            )
        )

        log.info("Transfer created successfully with id ${saved.id}")

        return saved.toCreateTransferResponse()
    }

}