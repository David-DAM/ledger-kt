package com.davinchicoder.ledgerkt.account

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class AccountEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    val userId: String,

    val currency: String = "EUR"
)