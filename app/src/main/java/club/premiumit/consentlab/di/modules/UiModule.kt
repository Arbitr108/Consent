package club.premiumit.consentlab.di.modules

import club.premiumit.consentlab.di.provider.DiProvider
import club.premiumit.consentlab.domain.usecase.CalculateUseCase
import club.premiumit.consentlab.domain.usecase.CostAdjustUseCase
import club.premiumit.consentlab.domain.usecase.GetGrantedUseCase
import club.premiumit.consentlab.domain.usecase.SaveGrantedUseCase
import club.premiumit.consentlab.ui.viewmodel.MainViewModel

fun uiModule(){
    DiProvider.di.add(MainViewModel::class, MainViewModel(
        calculateUseCase = DiProvider.di.get(CalculateUseCase::class),
        costAdjustUseCase = DiProvider.di.get(CostAdjustUseCase::class),
        saveGrantedUseCase = DiProvider.di.get(SaveGrantedUseCase::class),
        getGrantedUseCase = DiProvider.di.get(GetGrantedUseCase::class)
    ))
}