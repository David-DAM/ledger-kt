package com.davinchicoder.ledgerkt.account.infrastructure

import org.springframework.stereotype.Repository
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Repository
class AccountRepository(
    private val repository: AccountQueryRepository
) {

    fun getAccount(accountId: UUID) = repository.findById(accountId).getOrNull()?.toDomain()
    fun getAll() = repository.findAll().map { it.toDomain() }

}