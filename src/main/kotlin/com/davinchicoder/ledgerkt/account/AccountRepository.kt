package com.davinchicoder.ledgerkt.account

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AccountRepository(
    private val repository: AccountQueryRepository
) {

    fun getAccount(accountId: UUID) = repository.findById(accountId)
    fun getAll() = repository.findAll()

}