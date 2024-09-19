package club.premiumit.consentlab.di.modules

import club.premiumit.consentlab.di.provider.DiImpl
import club.premiumit.consentlab.di.provider.DiProvider
import club.premiumit.consentlab.ConsentEngineInitializer
import club.premiumit.consentlab.InitScope
import club.premiumit.consentlab.Initializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

fun appModule(){
    initialize()
    dataModule()
    domainModule()
    uiModule()
}

private fun initialize() {
    DiProvider.di = DiImpl()
    DiProvider.di.add(InitScope::class, InitScope(SupervisorJob() + Dispatchers.Default))
    DiProvider.di.add(
        Initializer::class, ConsentEngineInitializer(
            DiProvider.di.get(InitScope::class)
        )
    )
}


