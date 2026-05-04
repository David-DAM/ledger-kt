package com.davinchicoder.ledgerkt.ledger.infrastructure

import com.davinchicoder.ledgerkt.ledger.domain.LedgerEntry
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class LedgerEntryRepository(
    private val repository: LedgerEntryQueryRepository
) {

    fun saveAll(entries: List<LedgerEntry>) = repository.saveAll(entries.map { it.toEntity() })
    fun sumByAccount(accountId: UUID) = repository.sumByAccount(accountId)
}