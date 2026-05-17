package com.davinchicoder.ledgerkt.ledger.infrastructure

import com.davinchicoder.ledgerkt.ledger.domain.LedgerEntry
import java.util.*

fun LedgerEntry.toEntity() = LedgerEntryEntity(
    id = id,
    transactionId = transactionId,
    accountId = accountId,
    amount = amount,
    type = type,
    currency = currency.currencyCode,
    createdAt = createdAt
)

fun LedgerEntryEntity.toDomain() = LedgerEntry(
    id = id,
    transactionId = transactionId,
    accountId = accountId,
    amount = amount,
    type = type,
    currency = Currency.getInstance(currency),
    createdAt = createdAt
)