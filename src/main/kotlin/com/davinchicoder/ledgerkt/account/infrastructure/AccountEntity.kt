package com.davinchicoder.ledgerkt.account.infrastructure

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Table(name = "accounts")
@Entity
class AccountEntity(
    @Id
    var id: UUID = UUID.randomUUID(),

    var userId: String,

    var currency: String
)