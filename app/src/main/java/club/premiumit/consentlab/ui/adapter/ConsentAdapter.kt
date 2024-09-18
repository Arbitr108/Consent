package club.premiumit.consentlab.ui.adapter

import android.content.Context
import com.usercentrics.sdk.BannerSettings
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsBanner
import com.usercentrics.sdk.UsercentricsCMPData
import com.usercentrics.sdk.UsercentricsServiceConsent
import com.usercentrics.sdk.errors.UsercentricsError
import kotlinx.collections.immutable.ImmutableList

class ConsentAdapter(
    private val context: Context,
    private val onConsent: (List<UsercentricsServiceConsent>?) -> Unit,
    private val onFailure: (UsercentricsError) -> Unit,
    private val onCollectConsentData: (UsercentricsCMPData) -> Unit,
    private val bannerSettings: BannerSettings? = null
) {
    fun provideConsentValues() = collectDataSafe { data -> onCollectConsentData(data) }

    fun showBanner() {
        runSafe(
            initialBlock = {
                val banner = UsercentricsBanner(context, bannerSettings)
                banner.showFirstLayer { response ->
                    onConsent(response?.consents)
                }
            },
            defaultBlock = {
                val banner = UsercentricsBanner(context, bannerSettings)
                banner.showSecondLayer { response ->
                    onConsent(response?.consents)
                }
            },
            onFail = { error ->
                onFailure(error)
            }
        )
    }
}

private fun runSafe(
    initialBlock: () -> Unit,
    defaultBlock: () -> Unit,
    onFail: (UsercentricsError) -> Unit,
    applyConsent: (List<UsercentricsServiceConsent>) -> Unit = {}
) =
    Usercentrics.isReady(
        onSuccess = { state ->
            if (state.shouldCollectConsent) {
                initialBlock()
            } else {
                defaultBlock()
            }
        },
        onFailure = { error ->
            onFail(error)
        }
    )

private fun collectDataSafe(block: (data: UsercentricsCMPData) -> Unit)=
    Usercentrics.isReady(
        onSuccess = { _ ->
            block(Usercentrics.instance.getCMPData())
        },
        //TODO add logic for failure
        onFailure = {}
    )