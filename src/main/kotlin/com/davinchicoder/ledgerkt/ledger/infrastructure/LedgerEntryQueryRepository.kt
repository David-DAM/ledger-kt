package com.davinchicoder.ledgerkt.ledger.infrastructure

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.math.BigDecimal
import java.util.*

interface LedgerEntryQueryRepository : JpaRepository<LedgerEntryEntity, UUID> {

    @Query(
        """
        SELECT COALESCE(SUM(
                    CASE 
                        WHEN type = 'CREDIT' THEN amount
                        WHEN type = 'DEBIT' THEN -amount
                    END
                ), 0)
                FROM ledger_entries
                WHERE account_id = :accountId
    """, nativeQuery = true
    )
    fun sumByAccount(accountId: UUID): BigDecimal

}