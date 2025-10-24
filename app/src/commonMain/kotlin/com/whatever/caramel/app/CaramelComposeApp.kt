package com.whatever.caramel.app

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.whatever.caramel.core.designsystem.components.CaramelDialog
import com.whatever.caramel.core.designsystem.components.CaramelSnackBarHost
import com.whatever.caramel.core.designsystem.components.CaramelSnackbar
import com.whatever.caramel.core.designsystem.components.DefaultCaramelDialogLayout
import com.whatever.caramel.core.designsystem.components.LocalSnackbarHostState
import com.whatever.caramel.core.designsystem.components.showSnackbarMessage
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.inAppReview.CaramelInAppReview
import com.whatever.caramel.feature.copule.connecting.navigation.navigateToConnectingCouple
import com.whatever.caramel.feature.copule.invite.navigation.navigateToInviteCouple
import com.whatever.caramel.feature.login.navigation.navigateToLogin
import com.whatever.caramel.feature.main.navigation.navigateToMain
import com.whatever.caramel.feature.profile.create.navigation.navigateToCreateProfile
import com.whatever.caramel.mvi.AppIntent
import com.whatever.caramel.mvi.AppSideEffect
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CaramelComposeApp(
    navHostController: NavHostController,
    viewModel: CaramelViewModel = koinViewModel(),
    inAppReview: CaramelInAppReview = koinInject(),
) {
    CaramelTheme {
        val snackBarHostState = remember { SnackbarHostState() }

        CompositionLocalProvider(
            LocalSnackbarHostState provides snackBarHostState,
            LocalOverscrollFactory provides null,
        ) {
            val appState by viewModel.state.collectAsStateWithLifecycle()

            if (appState.isShowErrorDialog) {
                CaramelDialog(
                    show = appState.isShowErrorDialog,
                    title = appState.dialogMessage,
                    message = appState.dialogDescription,
                    mainButtonText = "확인",
                    onDismissRequest = { viewModel.intent(AppIntent.CloseErrorDialog) },
                    onMainButtonClick = { viewModel.intent(AppIntent.CloseErrorDialog) },
                ) {
                    DefaultCaramelDialogLayout()
                }
            }

            CaramelScaffold(
                snackBarHost = {
                    CaramelSnackBarHost(
                        modifier =
                            Modifier
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp,
                                    bottom = 20.dp,
                                ).imePadding(),
                        hostState = LocalSnackbarHostState.current,
                        snackbar = { snackbarData ->
                            CaramelSnackbar(
                                snackbarData = snackbarData,
                            )
                        },
                    )
                },
            ) {
                LaunchedEffect(Unit) {
                    viewModel.sideEffect.collect { sideEffect ->
                        when (sideEffect) {
                            is AppSideEffect.NavigateToInviteCoupleScreen -> {
                                navHostController.navigateToInviteCouple {
                                    popUpTo(0) {
                                        inclusive = true
                                    }
                                }
                            }

                            is AppSideEffect.NavigateToConnectingCoupleScreen -> {
                                navHostController.navigateToConnectingCouple {
                                    popUpTo(0) {
                                        inclusive = true
                                    }
                                }
                            }

                            is AppSideEffect.NavigateToCreateProfile -> {
                                navHostController.navigateToCreateProfile {
                                    popUpTo(0) {
                                        inclusive = true
                                    }
                                }
                            }

                            is AppSideEffect.NavigateToLogin -> {
                                navHostController.navigateToLogin {
                                    popUpTo(0) {
                                        inclusive = true
                                    }
                                }
                            }

                            is AppSideEffect.NavigateToMain -> {
                                navHostController.navigateToMain {
                                    popUpTo(0) {
                                        inclusive = true
                                    }
                                }
                            }

                            is AppSideEffect.ShowToast ->
                                showSnackbarMessage(
                                    coroutineScope = this,
                                    snackbarHostState = snackBarHostState,
                                    message = sideEffect.message,
                                )

                            AppSideEffect.RequestInAppReview -> inAppReview.requestReview()
                        }
                    }
                }
                CaramelNavHost(
                    modifier = Modifier,
                    navHostController = navHostController,
                    onIntent = { intent -> viewModel.intent(intent) },
                )
            }
        }
    }
}
