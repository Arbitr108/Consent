package club.premiumit.consentlab.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import club.premiumit.consentlab.R
import club.premiumit.consentlab.ui.viewmodel.UiState

@Composable
fun MainScreenContent(
    uiState: UiState,
    onShowConsentBannerClicked: () -> Unit,
    modifier: Modifier = Modifier) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxHeight()
                .align(Alignment.Center)
        ) {
            Text(
                text = uiState.score,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(R.string.consent_score),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Button(
            onClick = onShowConsentBannerClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.show_consent_banner),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.surface,
            )
        }
    }
}