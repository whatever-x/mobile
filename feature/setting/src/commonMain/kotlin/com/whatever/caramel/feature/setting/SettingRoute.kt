package com.whatever.caramel.feature.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.ui.util.ObserveLifecycleEvent
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingSideEffect
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SettingRoute(
    viewModel: SettingViewModel = koinViewModel(),
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToEditCountDown: (String) -> Unit,
    navigateToEditBirthday: (String) -> Unit,
    navigateToEditNickName: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current
    val privacyPolicyUrl = stringResource(Resources.String.privacy_policy_url)
    val termsOfServiceUrl = stringResource(Resources.String.terms_of_service_url)

    ObserveLifecycleEvent { event ->
        if (event == Lifecycle.Event.ON_START) {
            viewModel.intent(SettingIntent.RefreshCoupleData)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                SettingSideEffect.OpenPrivacyPolicy -> uriHandler.openUri(privacyPolicyUrl)
                SettingSideEffect.OpenTermsOfService -> uriHandler.openUri(termsOfServiceUrl)
                SettingSideEffect.NavigateLogin -> navigateToLogin()
                is SettingSideEffect.NavigateToHome -> navigateToHome()
                is SettingSideEffect.NavigateToEditCountDown -> navigateToEditCountDown(sideEffect.startDate)
                is SettingSideEffect.NavigateToEditNickname -> navigateToEditNickName(sideEffect.nickname)
                is SettingSideEffect.NavigateToEditBirthday -> navigateToEditBirthday(sideEffect.birthday)
            }
        }
    }

    SettingScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}