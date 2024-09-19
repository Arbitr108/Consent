package club.premiumit.consentlab

import android.app.Application
import club.premiumit.consentlab.di.modules.appModule
import club.premiumit.consentlab.di.provider.consentDi
import club.premiumit.consentlab.ui.adapter.Initializer

class ConsentApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Init dependencies
        appModule()

        val consentEnginStart by consentDi<Initializer>()
        consentEnginStart(this)
    }

}