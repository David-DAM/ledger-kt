package com.davinchicoder.ledgerkt

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
data class LedgerEntry(
    @Id
    val id: UUID = UUID.randomUUID(),

    val accountId: UUID,

    val amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    val type: EntryType,

    val createdAt: Instant = Instant.now()
)
