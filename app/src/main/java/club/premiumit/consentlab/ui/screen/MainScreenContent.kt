package club.premiumit.consentlab.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import club.premiumit.consentlab.R
import club.premiumit.consentlab.ui.theme.ConsentLabTheme
import club.premiumit.consentlab.ui.theme.buttonBackground
import club.premiumit.consentlab.ui.viewmodel.UiState
import club.premiumit.consentlab.ui.widgets.LoadingIndicator

/**
 *  Created by Pavel Gorbatiuk
 *  [https://premiumIt.club]
 **/
@Composable
fun MainScreenContent(
    uiState: UiState,
    onShowConsentBannerClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxHeight()
                .align(Alignment.Center)
        ) {
            Text(
                text = uiState.score,
                style = MaterialTheme.typography.titleLarge
                    .copy(fontSize = 120.sp),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(R.string.consent_score),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        TextButton(
            modifier = Modifier
                .background(
                    color = buttonBackground,
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .height(54.dp)
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors().copy(
                contentColor = buttonBackground,
                containerColor = buttonBackground,
            ),
            contentPadding = PaddingValues(0.dp),
            onClick = onShowConsentBannerClicked
        ) {
            Text(
                text = stringResource(id = R.string.show_consent_banner),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSecondary,
                textAlign = TextAlign.Center
            )
        }

        if (uiState.isLoading) {
            LoadingIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }

}

@Preview
@Composable
private fun MainScreenContentPreview() {
    ConsentLabTheme {
        MainScreenContent(
            uiState = UiState(score = "0", isLoading = false),
            onShowConsentBannerClicked = {}
        )
    }
}