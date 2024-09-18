package club.premiumit.consentlab.di.modules

import club.premiumit.consentlab.data.repository.ConsentsRepositoryImpl
import club.premiumit.consentlab.data.repository.CostsRepositoryImpl
import club.premiumit.consentlab.data.repository.RulesRepositoryImpl
import club.premiumit.consentlab.data.source.RemoteCostsService
import club.premiumit.consentlab.di.provider.DiProvider
import club.premiumit.consentlab.domain.repository.ConsentsRepository
import club.premiumit.consentlab.domain.repository.CostsRepository
import club.premiumit.consentlab.domain.repository.RulesRepository

internal fun dataModule() {
    DiProvider.di
        .add(RemoteCostsService::class, RemoteCostsService())

        .add(
            CostsRepository::class, CostsRepositoryImpl(
                remoteCostsService = DiProvider.di.get(RemoteCostsService::class)
            )
        )
        .add(
            RulesRepository::class, RulesRepositoryImpl(
                remoteCostsService = DiProvider.di.get(RemoteCostsService::class)
            )
        )
        .add(ConsentsRepository::class, ConsentsRepositoryImpl())
}