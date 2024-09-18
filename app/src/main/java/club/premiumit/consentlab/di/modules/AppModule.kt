package club.premiumit.consentlab.di.modules

import club.premiumit.consentlab.di.provider.DiImpl
import club.premiumit.consentlab.di.provider.DiProvider

fun appModule(){
    DiProvider.di = DiImpl()
    dataModule()
    domainModule()
    uiModule()
}


