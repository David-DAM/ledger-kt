package com.davinchicoder.ledgerkt.account.infrastructure

import com.davinchicoder.ledgerkt.account.domain.Account

fun Account.toEntity() = AccountEntity(id = id, userId = userId, currency = currency)

fun AccountEntity.toDomain() = Account(id = id, userId = userId, currency = currency)