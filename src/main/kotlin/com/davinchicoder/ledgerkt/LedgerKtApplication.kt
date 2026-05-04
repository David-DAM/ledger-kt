package com.davinchicoder.ledgerkt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class LedgerKtApplication

fun main(args: Array<String>) {
    runApplication<LedgerKtApplication>(*args)
}
