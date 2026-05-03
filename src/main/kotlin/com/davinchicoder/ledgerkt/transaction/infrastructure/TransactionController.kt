package com.davinchicoder.ledgerkt.transaction.infrastructure

import com.davinchicoder.ledgerkt.transaction.application.CreateTransferRequest
import com.davinchicoder.ledgerkt.transaction.application.CreateTransferUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/ledger")
@RestController
class TransactionController(
    private val transferUseCase: CreateTransferUseCase
) {
    @PostMapping("/transfer")
    fun createTransfer(@RequestBody request: CreateTransferRequest) = transferUseCase.handle(request)
}