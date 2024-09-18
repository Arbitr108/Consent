package club.premiumit.consentlab

import android.app.Application
import club.premiumit.consentlab.di.modules.appModule
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel

class ConsentApp : Application() {

    override fun onCreate() {
        super.onCreate()
        val options = UsercentricsOptions(
            settingsId = BuildConfig.SETTINGS_ID,
            loggerLevel =
            if (BuildConfig.DEBUG) UsercentricsLoggerLevel.DEBUG
            else UsercentricsLoggerLevel.ERROR
        )
        // Init dependencies
        appModule()

        // TODO: use AndroidViewModel to initialize in background
        Usercentrics.initialize(this, options)
    }
}