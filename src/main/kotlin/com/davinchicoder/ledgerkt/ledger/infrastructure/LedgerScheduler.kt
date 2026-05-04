package com.davinchicoder.ledgerkt.ledger.infrastructure

import com.davinchicoder.ledgerkt.account.infrastructure.AccountRepository
import com.davinchicoder.ledgerkt.common.logger
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Profile("!test")
@Component
class LedgerScheduler(
    private val ledgerEntryRepository: LedgerEntryRepository,
    private val accountRepository: AccountRepository,
) {
    companion object {
        private val log = logger<LedgerScheduler>()
        val semaphore = Semaphore(10)
    }

    @Scheduled(cron = "0 * * * * *")
    fun reconcile() = runBlocking {
        log.info("Reconciling ledger")
        val accounts = accountRepository.getAll()

        coroutineScope {
            accounts.map { account ->
                async(Dispatchers.IO) {
                    semaphore.withPermit {
                        val ledgerBalance = ledgerEntryRepository.sumByAccount(account.id)

                        if (ledgerBalance.compareTo(BigDecimal.ZERO) != 0) {
                            log.warn("Mismatch in ${account.id}")
                        }
                    }
                }
            }.awaitAll()
            log.info("Reconciliation complete")
        }
    }

}