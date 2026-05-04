package com.davinchicoder.ledgerkt.ledger

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class LedgerEntryRepository(
    private val repository: LedgerEntryQueryRepository
) {

    fun saveAll(entries: List<LedgerEntryEntity>) = repository.saveAll(entries)
    fun sumByAccount(accountId: UUID) = repository.sumByAccount(accountId)
}