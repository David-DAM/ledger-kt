package com.davinchicoder.ledgerkt.ledger

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface LedgerEntryQueryRepository : JpaRepository<LedgerEntryEntity, UUID> {
}