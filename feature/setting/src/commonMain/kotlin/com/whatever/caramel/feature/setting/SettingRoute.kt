package com.whatever.caramel.feature.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feature.setting.mvi.SettingSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SettingRoute(
    viewModel: SettingViewModel = koinViewModel(),
    navigateToHome: () -> Unit,
    navigateToEditBirthday: () -> Unit,
    navigateToEditNickName: () -> Unit,
    navigateToLogin : () -> Unit,
    navigateToEditCountDown: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SettingSideEffect.NavigateToHome -> navigateToHome()
                is SettingSideEffect.NavigateToEditCountDown -> navigateToEditCountDown() // TODO : D-day 설정 시 millisecond 사용
                SettingSideEffect.NavigateToPersonalInfoTermNotion -> TODO()
                SettingSideEffect.NavigateToServiceTermNotion -> TODO()
                SettingSideEffect.NavigateLogin -> navigateToLogin()
                SettingSideEffect.NavigateToEditNickname -> navigateToEditNickName()
                SettingSideEffect.NavigateToEditBirthDay -> navigateToEditBirthday()
            }
        }
    }

    SettingScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}