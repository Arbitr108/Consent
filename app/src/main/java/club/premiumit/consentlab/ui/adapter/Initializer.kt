package club.premiumit.consentlab.ui.adapter

import android.app.Application
import club.premiumit.consentlab.BuildConfig
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class InitScope(override val coroutineContext: CoroutineContext) : CoroutineScope

fun interface Initializer {
    operator fun invoke(context: Application)
}

class ConsentEngineInitializer(
    private val initScope: InitScope
) : Initializer {

    override fun invoke(context: Application) {
        initScope.launch {
            val options = UsercentricsOptions(
                settingsId = BuildConfig.SETTINGS_ID,
                loggerLevel =
                if (BuildConfig.DEBUG) UsercentricsLoggerLevel.DEBUG
                else UsercentricsLoggerLevel.ERROR
            )
            Usercentrics.initialize(context, options)
        }
    }
}