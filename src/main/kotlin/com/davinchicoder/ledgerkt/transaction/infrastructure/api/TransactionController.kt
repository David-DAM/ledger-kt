package com.davinchicoder.ledgerkt.transaction.infrastructure.api

import com.davinchicoder.ledgerkt.common.logger
import com.davinchicoder.ledgerkt.transaction.application.CreateTransferUseCase
import com.davinchicoder.ledgerkt.transaction.infrastructure.toDto
import com.davinchicoder.ledgerkt.transaction.infrastructure.toRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/ledger")
@RestController
class TransactionController(
    private val transferUseCase: CreateTransferUseCase
) {

    companion object {
        private val log = logger<TransactionController>()
    }

    @PostMapping("/transfer")
    fun createTransfer(@RequestBody dto: CreateTransferDto): CreatedTransferDto {
        log.info("Creating transfer with request: $dto")
        val result = transferUseCase.handle(dto.toRequest())
        log.info("Transfer created with ID: ${result.id}")
        return result.toDto()
    }
}