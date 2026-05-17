package com.davinchicoder.ledgerkt.ledger.infrastructure

import com.davinchicoder.ledgerkt.ledger.domain.LedgerEntry

fun LedgerEntry.toEntity() = LedgerEntryEntity(
    id = id,
    accountId = accountId,
    amount = amount,
    type = type,
    createdAt = createdAt
)

fun LedgerEntryEntity.toDomain() = LedgerEntry(
    id = id,
    accountId = accountId,
    amount = amount,
    type = type,
    createdAt = createdAt
)