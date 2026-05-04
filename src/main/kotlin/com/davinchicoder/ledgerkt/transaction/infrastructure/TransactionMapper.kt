package com.davinchicoder.ledgerkt.transaction.infrastructure

import com.davinchicoder.ledgerkt.transaction.application.CreateTransferRequest
import com.davinchicoder.ledgerkt.transaction.application.CreateTransferResponse
import com.davinchicoder.ledgerkt.transaction.domain.Transaction
import com.davinchicoder.ledgerkt.transaction.infrastructure.api.CreateTransferDto
import com.davinchicoder.ledgerkt.transaction.infrastructure.api.CreatedTransferDto
import com.davinchicoder.ledgerkt.transaction.infrastructure.database.TransactionEntity

fun Transaction.toEntity() = TransactionEntity(id, idempotencyKey, fromAccount, toAccount, amount, createdAt)

fun TransactionEntity.toDomain() = Transaction(id, idempotencyKey, fromAccount, toAccount, amount, createdAt)

fun CreateTransferDto.toRequest() = CreateTransferRequest(idempotencyKey, fromAccountId, toAccountId, amount)

fun CreateTransferResponse.toDto() = CreatedTransferDto(id, createdAt)