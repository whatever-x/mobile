package com.whatever.caramel.feat.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.feat.setting.mvi.SettingSideEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SettingRoute(
    viewModel: SettingViewModel = koinViewModel(),
    navigateToHome: () -> Unit,
    navigateToEditBirthday: () -> Unit,
    navigateToEditNickName: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SettingSideEffect.NavigateToHome -> navigateToHome()
                is SettingSideEffect.NavigateToEditNickName -> navigateToEditNickName()
                is SettingSideEffect.NavigateToEditBirthday -> navigateToEditBirthday()
            }
        }
    }

    SettingScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}