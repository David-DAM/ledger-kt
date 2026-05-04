package com.davinchicoder.ledgerkt.ledger.infrastructure

import com.davinchicoder.ledgerkt.ledger.domain.LedgerEntry

fun LedgerEntry.toEntity() = LedgerEntryEntity(id, accountId, amount, type, createdAt)

fun LedgerEntryEntity.toDomain() = LedgerEntry(id, accountId, amount, type, createdAt)