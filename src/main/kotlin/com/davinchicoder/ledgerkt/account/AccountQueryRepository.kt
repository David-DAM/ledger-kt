package com.davinchicoder.ledgerkt.account

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AccountQueryRepository : JpaRepository<AccountEntity, UUID> {
}