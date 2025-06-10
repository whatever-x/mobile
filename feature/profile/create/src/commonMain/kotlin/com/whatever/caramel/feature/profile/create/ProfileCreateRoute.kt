package com.whatever.caramel.feature.profile.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.designsystem.foundations.Resources
import com.whatever.caramel.core.designsystem.util.HapticController
import com.whatever.caramel.core.designsystem.util.HapticStyle
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateIntent
import com.whatever.caramel.feature.profile.create.mvi.ProfileCreateSideEffect
import org.koin.compose.getKoin
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ProfileCreateRoute(
    viewModel: ProfileCreateViewModel = koinViewModel(),
    navigateToLogin: () -> Unit,
    navigateToStartDestination: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val hapticController: HapticController = getKoin().get()
    val urlHandler = LocalUriHandler.current
    val privacyPolicyUrl = stringResource(Resources.String.privacy_policy_url)
    val termsOfServiceUrl = stringResource(Resources.String.terms_of_service_url)

    BackHandler {
        viewModel.intent(ProfileCreateIntent.ClickSystemNavigationBackButton)
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is ProfileCreateSideEffect.NavigateToLogin -> navigateToLogin()
                is ProfileCreateSideEffect.NavigateToStartDestination -> navigateToStartDestination()
                is ProfileCreateSideEffect.NavigateToPersonalInfoTermNotion -> urlHandler.openUri(privacyPolicyUrl)
                is ProfileCreateSideEffect.NavigateToServiceTermNotion -> urlHandler.openUri(termsOfServiceUrl)
                is ProfileCreateSideEffect.PerformHapticFeedback -> hapticController.performImpact(HapticStyle.GestureThresholdActivate)
                is ProfileCreateSideEffect.ShowErrorSnackBar -> Napier.d { "error ${sideEffect.message}" } // @RyuSw-cs TODO : 스낵바 표시
            }
        }
    }

    ProfileCreateScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}