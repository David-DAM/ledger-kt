package com.davinchicoder.ledgerkt.transaction.application

import com.davinchicoder.ledgerkt.account.AccountRepository
import com.davinchicoder.ledgerkt.common.logger
import com.davinchicoder.ledgerkt.ledger.EntryType
import com.davinchicoder.ledgerkt.ledger.LedgerEntryEntity
import com.davinchicoder.ledgerkt.ledger.LedgerEntryRepository
import com.davinchicoder.ledgerkt.transaction.infrastructure.TransactionEntity
import com.davinchicoder.ledgerkt.transaction.infrastructure.TransactionRepository
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

        if (existingTransaction.isPresent) {
            log.info("Transaction already exists")
            return CreateTransferResponse(
                id = existingTransaction.get().id,
                createdAt = existingTransaction.get().createdAt
            )
        }

        val fromAccount = accountRepository.getAccount(request.fromAccountId)
        val toAccount = accountRepository.getAccount(request.toAccountId)

        if (fromAccount.isEmpty || toAccount.isEmpty) {
            log.error("Account not found")
            throw IllegalArgumentException("Account not found")
        }

        val transaction = TransactionEntity(
            idempotencyKey = request.idempotencyKey,
            fromAccount = fromAccount.get().id,
            toAccount = toAccount.get().id,
            amount = request.amount,
        )

        val saved = transactionRepository.save(transaction)

        ledgerRepository.save(
            LedgerEntryEntity(
                amount = request.amount,
                type = EntryType.DEBIT,
                accountId = fromAccount.get().id
            )
        )

        ledgerRepository.save(
            LedgerEntryEntity(
                amount = request.amount,
                type = EntryType.CREDIT,
                accountId = toAccount.get().id
            )
        )

        log.info("Transfer created successfully with id ${saved.id}")

        return CreateTransferResponse(
            id = saved.id,
            createdAt = saved.createdAt
        )
    }

}