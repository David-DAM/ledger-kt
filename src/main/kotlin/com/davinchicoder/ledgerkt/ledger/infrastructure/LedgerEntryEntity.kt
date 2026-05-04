package com.davinchicoder.ledgerkt.ledger.infrastructure

import com.davinchicoder.ledgerkt.ledger.domain.EntryType
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Table(name = "ledger_entries")
@Entity
data class LedgerEntryEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    val accountId: UUID,

    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    val type: EntryType,

    val createdAt: Instant = Instant.now()
)
