package com.davinchicoder.ledgerkt.transaction.infrastructure

import com.davinchicoder.ledgerkt.transaction.application.CreateTransferRequest
import com.davinchicoder.ledgerkt.transaction.application.CreateTransferResponse
import com.davinchicoder.ledgerkt.transaction.domain.Transaction
import com.davinchicoder.ledgerkt.transaction.infrastructure.api.CreateTransferDto
import com.davinchicoder.ledgerkt.transaction.infrastructure.api.CreatedTransferDto
import com.davinchicoder.ledgerkt.transaction.infrastructure.database.TransactionEntity
import java.time.Instant
import java.util.*

fun Transaction.toEntity() = TransactionEntity(
    id = id,
    idempotencyKey = idempotencyKey,
    fromAccount = fromAccount,
    toAccount = toAccount,
    amount = amount,
    fromCurrency = fromCurrency.currencyCode,
    toCurrency = toCurrency.currencyCode,
    createdAt = createdAt
)

fun TransactionEntity.toDomain() = Transaction(
    id = id,
    idempotencyKey = idempotencyKey,
    fromAccount = fromAccount,
    toAccount = toAccount,
    amount = amount,
    fromCurrency = Currency.getInstance(fromCurrency),
    toCurrency = Currency.getInstance(toCurrency),
    createdAt = createdAt
)

fun transactionFromCsv(line: String): Transaction {
    val parts = line.split(",")
    return Transaction(
        id = UUID.randomUUID(),
        idempotencyKey = UUID.randomUUID(),
        fromAccount = UUID.fromString(parts[0]),
        toAccount = UUID.fromString(parts[1]),
        amount = parts[2].toBigDecimal(),
        fromCurrency = Currency.getInstance(parts[3]),
        toCurrency = Currency.getInstance(parts[4]),
        createdAt = Instant.now()
    )
}

fun CreateTransferDto.toRequest() = CreateTransferRequest(
    idempotencyKey = idempotencyKey,
    fromAccountId = fromAccountId,
    toAccountId = toAccountId,
    amount = amount,
    fromCurrency = fromCurrency,
    toCurrency = toCurrency
)

fun CreateTransferResponse.toDto() = CreatedTransferDto(id = id, createdAt = createdAt)