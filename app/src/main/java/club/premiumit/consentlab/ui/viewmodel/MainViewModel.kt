package club.premiumit.consentlab.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import club.premiumit.consentlab.domain.usecase.CalculateUseCase
import club.premiumit.consentlab.domain.usecase.CostAdjustUseCase
import club.premiumit.consentlab.domain.usecase.GetGrantedUseCase
import club.premiumit.consentlab.domain.usecase.SaveGrantedUseCase
import com.usercentrics.sdk.UsercentricsCMPData
import com.usercentrics.sdk.UsercentricsServiceConsent
import com.usercentrics.sdk.errors.UsercentricsError
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val DEFAULT_VALUE = "0"

@Immutable
data class UiState(
    val score: String
) {
    companion object {
        fun default() = UiState(score = DEFAULT_VALUE)
    }
}

sealed interface MainEvent {
    data object ShowBanner : MainEvent
    data class OnConsent(val consents: List<UsercentricsServiceConsent>?) : MainEvent
    data class OnFailure(val error: UsercentricsError) : MainEvent
    data class OnCollectConsentData(val data: UsercentricsCMPData) : MainEvent
}

sealed interface MainEffect {
    data object ShowBanner : MainEffect
    data object CollectConsentData: MainEffect
}

class MainViewModel(
    private val calculateUseCase: CalculateUseCase,
    private val costAdjustUseCase: CostAdjustUseCase,
    private val saveGrantedUseCase: SaveGrantedUseCase,
    private val getGrantedUseCase: GetGrantedUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState.default())
    val uiState = _uiState.asStateFlow()
    private val _effects = MutableStateFlow<MainEffect?>(null)
    val effects = _effects.asSharedFlow()

    fun onEvent(event: MainEvent) = when (event) {
        is MainEvent.OnConsent -> onConsent(event.consents)
        is MainEvent.ShowBanner -> showConsentBanner()
        is MainEvent.OnFailure -> onFailure(event.error)
        is MainEvent.OnCollectConsentData -> onCollectConsentData(event.data)
    }

    fun effectConsumed() {
        _effects.value = null
    }

    private fun onCollectConsentData(data: UsercentricsCMPData) {
        viewModelScope.launch {
            val calculations = mutableMapOf<String, Deferred<Double>>()
            val granted = getGrantedUseCase()

            // Calculate every declared cost with rules application
            data.services.asSequence()
                .filterNot { it.dataProcessor == null }
                .forEach { service ->
                    calculations[service.dataProcessor!!] = async {
                        costAdjustUseCase(
                            service.dataCollectedList,
                            calculateUseCase(service.dataCollectedList)
                        )
                    }
                }

            // Calculate the total of granted
            val results = calculations
                .filter { granted.contains(it.key) }
                .map { it.key to it.value.await() }

            val total = results.sumOf { it.second }

            renderToConsole(results, total)

            _uiState.update {
                it.copy(score = total.toString())
            }
        }
    }

    private fun renderToConsole(
        results: List<Pair<String, Double>>,
        total: Double
    ) {
        println(results.joinToString { "\n\t${it.first} = ${it.second}" })
        println("Total: $total")
    }

    private val showConsentBanner: () -> Unit = {
        _effects.update {
            MainEffect.ShowBanner
        }
    }

    private fun onConsent(consents: List<UsercentricsServiceConsent>?) {
        val granted = consents?.asSequence()?.filter { it.status }
        println("PVD: onConsent: \n\t${granted?.joinToString("\n")}")
        if (granted != null) {
            viewModelScope.launch {
                saveGrantedUseCase(granted.map { it.dataProcessor }.toList())
                _effects.update { MainEffect.CollectConsentData }
            }
        }
    }

    private fun onFailure(error: UsercentricsError) {
        println("PVD: onFailure: \n\t${error.message}")
    }

    internal class Factory(
        private val calculateUseCase: CalculateUseCase,
        private val costAdjustUseCase: CostAdjustUseCase,
        private val saveGrantedUseCase: SaveGrantedUseCase,
        private val getGrantedUseCase: GetGrantedUseCase
    ) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(
                calculateUseCase = calculateUseCase,
                costAdjustUseCase = costAdjustUseCase,
                saveGrantedUseCase = saveGrantedUseCase,
                getGrantedUseCase = getGrantedUseCase
            ) as T
    }
}