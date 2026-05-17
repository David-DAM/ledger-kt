package com.davinchicoder.ledgerkt.ledger.infrastructure

import com.davinchicoder.ledgerkt.ledger.domain.EntryType
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Table(name = "ledger_entries")
@Entity
class LedgerEntryEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    var transactionId: UUID,

    var accountId: UUID,

    var amount: BigDecimal,

    var currency: String,

    @Enumerated(EnumType.STRING)
    var type: EntryType,

    var createdAt: Instant = Instant.now()
)
