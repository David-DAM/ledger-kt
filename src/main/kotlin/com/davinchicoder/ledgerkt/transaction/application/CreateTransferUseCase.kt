package com.davinchicoder.ledgerkt.transaction.application

import com.davinchicoder.ledgerkt.account.infrastructure.AccountRepository
import com.davinchicoder.ledgerkt.common.logger
import com.davinchicoder.ledgerkt.ledger.domain.EntryType
import com.davinchicoder.ledgerkt.ledger.domain.LedgerEntry
import com.davinchicoder.ledgerkt.ledger.infrastructure.LedgerEntryRepository
import com.davinchicoder.ledgerkt.transaction.domain.Transaction
import com.davinchicoder.ledgerkt.transaction.infrastructure.database.TransactionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CreateTransferUseCase(
    private val transactionRepository: TransactionRepository,
    private val accountRepository: AccountRepository,
    private val ledgerRepository: LedgerEntryRepository
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

        val transaction = Transaction(
            idempotencyKey = request.idempotencyKey,
            fromAccount = fromAccount.id,
            toAccount = toAccount.id,
            amount = request.amount,
        )

        val saved = transactionRepository.save(transaction)

        ledgerRepository.saveAll(
            listOf(
                LedgerEntry(
                    amount = request.amount,
                    type = EntryType.DEBIT,
                    accountId = fromAccount.id
                ),
                LedgerEntry(
                    amount = request.amount,
                    type = EntryType.CREDIT,
                    accountId = toAccount.id
                )
            )
        )

        log.info("Transfer created successfully with id ${saved.id}")

        return saved.toCreateTransferResponse()
    }

}