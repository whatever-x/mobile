package com.whatever.caramel.feature.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.setting.mvi.SettingIntent
import com.whatever.caramel.feature.setting.mvi.SettingSideEffect
import com.whatever.caramel.core.ui.util.ObserveLifecycleEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SettingRoute(
    viewModel: SettingViewModel = koinViewModel(),
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToEditCountDown: (Long) -> Unit,
    navigateToEditBirthday: (Long) -> Unit,
    navigateToEditNickName: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveLifecycleEvent { event ->
        if (event == Lifecycle.Event.ON_START) {
            viewModel.intent(SettingIntent.RefreshCoupleData)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                SettingSideEffect.NavigateToPersonalInfoTermNotion -> TODO()
                SettingSideEffect.NavigateToServiceTermNotion -> TODO()
                SettingSideEffect.NavigateLogin -> navigateToLogin()
                is SettingSideEffect.NavigateToHome -> navigateToHome()
                is SettingSideEffect.NavigateToEditCountDown -> navigateToEditCountDown(sideEffect.startDateMillisecond)
                is SettingSideEffect.NavigateToEditNickname -> navigateToEditNickName(sideEffect.nickname)
                is SettingSideEffect.NavigateToEditBirthDay -> navigateToEditBirthday(sideEffect.birthdayMillisecond)
            }
        }
    }

    SettingScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}