package club.premiumit.consentlab.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import club.premiumit.consentlab.di.provider.consentDi
import club.premiumit.consentlab.domain.usecase.CalculateUseCase
import club.premiumit.consentlab.domain.usecase.CostAdjustUseCase
import club.premiumit.consentlab.domain.usecase.GetGrantedUseCase
import club.premiumit.consentlab.domain.usecase.SaveGrantedUseCase
import club.premiumit.consentlab.ui.adapter.ConsentAdapter
import club.premiumit.consentlab.ui.viewmodel.MainEffect
import club.premiumit.consentlab.ui.viewmodel.MainEvent
import club.premiumit.consentlab.ui.viewmodel.MainViewModel
import com.usercentrics.sdk.UsercentricsCMPData
import com.usercentrics.sdk.UsercentricsServiceConsent
import com.usercentrics.sdk.errors.UsercentricsError

/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel<MainViewModel>(
        factory = MainViewModel.Factory(
            calculateUseCase =  consentDi<CalculateUseCase>().value,
            costAdjustUseCase = consentDi<CostAdjustUseCase>().value,
            saveGrantedUseCase = consentDi<SaveGrantedUseCase>().value,
            getGrantedUseCase = consentDi<GetGrantedUseCase>().value
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val consentController = rememberConsentAdapter(
        context = LocalContext.current,
        onConsent = { result -> viewModel.onEvent(MainEvent.OnConsent(result)) },
        onFailure = { error -> viewModel.onEvent(MainEvent.OnFailure(error)) },
        onCollectConsentData = { data -> viewModel.onEvent(MainEvent.OnCollectConsentData(data)) }
    )
    val effect by viewModel.effects.collectAsStateWithLifecycle(initialValue = null)

    DisposableEffect(effect) {
        effect?.let {
            when (it) {
                is MainEffect.ShowBanner -> consentController.showBanner()
                is MainEffect.CollectConsentData -> consentController.provideConsentValues()
            }
        }
        onDispose {
            viewModel.effectConsumed()
        }

    }

    MainScreenContent(
        uiState = uiState,
        onShowConsentBannerClicked = { viewModel.onEvent(MainEvent.ShowBanner) })
}

@Composable
fun rememberConsentAdapter(
    context: Context,
    onConsent: (List<UsercentricsServiceConsent>?) -> Unit,
    onFailure: (UsercentricsError) -> Unit,
    onCollectConsentData: (UsercentricsCMPData) -> Unit
): ConsentAdapter = remember {
    ConsentAdapter(
        context = context,
        onConsent = { consents -> onConsent(consents) },
        onFailure = { error -> onFailure(error) },
        onCollectConsentData = { data -> onCollectConsentData(data) }
    )
}