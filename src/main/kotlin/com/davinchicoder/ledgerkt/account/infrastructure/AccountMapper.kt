package com.davinchicoder.ledgerkt.account.infrastructure

import com.davinchicoder.ledgerkt.account.domain.Account

fun Account.toEntity() = AccountEntity(id, userId, currency)

fun AccountEntity.toDomain() = Account(id, userId, currency)