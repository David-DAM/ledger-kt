package com.davinchicoder.ledgerkt.ledger

import org.springframework.stereotype.Repository

@Repository
class LedgerEntryRepository(
    private val repository: LedgerEntryQueryRepository
) {

    fun save(entry: LedgerEntryEntity) = repository.save(entry)

}