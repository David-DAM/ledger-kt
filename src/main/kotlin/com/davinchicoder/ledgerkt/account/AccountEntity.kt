package com.davinchicoder.ledgerkt.account

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "accounts")
@Entity
data class AccountEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    val userId: String,

    val currency: String
)