package com.davinchicoder.ledgerkt

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class Account(
    @Id
    val id: UUID = UUID.randomUUID(),

    val userId: String,

    val currency: String = "EUR"
)