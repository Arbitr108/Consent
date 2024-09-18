package club.premiumit.consentlab.di.modules

import club.premiumit.consentlab.di.provider.DiProvider
import club.premiumit.consentlab.domain.repository.ConsentsRepository
import club.premiumit.consentlab.domain.repository.CostsRepository
import club.premiumit.consentlab.domain.repository.RulesRepository
import club.premiumit.consentlab.domain.usecase.CalculateUseCase
import club.premiumit.consentlab.domain.usecase.CalculateUseCaseImpl
import club.premiumit.consentlab.domain.usecase.CostAdjustUseCase
import club.premiumit.consentlab.domain.usecase.CostAdjustUseCaseImpl
import club.premiumit.consentlab.domain.usecase.GetGrantedUseCase
import club.premiumit.consentlab.domain.usecase.GetGrantedUseCaseImpl
import club.premiumit.consentlab.domain.usecase.SaveGrantedUseCase
import club.premiumit.consentlab.domain.usecase.SaveGrantedUseCaseImpl

internal fun domainModule() {
    DiProvider.di
        .add(
            CalculateUseCase::class, CalculateUseCaseImpl(
                repository = DiProvider.di.get(CostsRepository::class)
            )
        )
        .add(CostAdjustUseCase::class, CostAdjustUseCaseImpl(
            repository = DiProvider.di.get(RulesRepository::class)
        ))
        .add(
            SaveGrantedUseCase::class, SaveGrantedUseCaseImpl(
                repository = DiProvider.di.get(ConsentsRepository::class)
            )
        )
        .add(
            GetGrantedUseCase::class, GetGrantedUseCaseImpl(
                repository = DiProvider.di.get(ConsentsRepository::class)
            )
        )
}