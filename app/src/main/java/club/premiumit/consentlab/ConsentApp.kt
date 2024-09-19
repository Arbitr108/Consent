package club.premiumit.consentlab

import android.app.Application
import club.premiumit.consentlab.di.modules.appModule
import club.premiumit.consentlab.di.provider.consentDi

/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
class ConsentApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appModule()

        val consentEnginStart by consentDi<Initializer>()
        consentEnginStart(this)
    }

}