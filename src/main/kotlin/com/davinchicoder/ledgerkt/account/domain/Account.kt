package com.davinchicoder.ledgerkt.account.domain

import java.util.*

data class Account(
    val id: UUID = UUID.randomUUID(),

    val userId: String,

    val currency: String
)