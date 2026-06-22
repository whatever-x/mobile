package com.whatever.caramel.feature.balancegame.share

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import caramel.feature.balancegame.generated.resources.Res
import caramel.feature.balancegame.generated.resources.balance_game_share_gallery_permission_required
import caramel.feature.balancegame.generated.resources.balance_game_share_save_failure
import caramel.feature.balancegame.generated.resources.balance_game_share_save_success
import caramel.feature.balancegame.generated.resources.balance_game_share_share_failure
import com.whatever.caramel.core.designsystem.components.LocalSnackbarHostState
import com.whatever.caramel.core.designsystem.components.showSnackbarMessage
import com.whatever.caramel.feature.balancegame.share.image.requiresLegacyGalleryWritePermission
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareIntent
import com.whatever.caramel.feature.balancegame.share.mvi.BalanceGameShareSideEffect
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.storage.WRITE_STORAGE
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun BalanceGameShareRoute(
    viewModel: BalanceGameShareViewModel = koinViewModel(),
    navigateToBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()
    val permissionFactory = rememberPermissionsControllerFactory()
    val permissionsController = remember(permissionFactory) { permissionFactory.createPermissionsController() }
    val imageSaveSuccessMessage =
        rememberUpdatedState(stringResource(Res.string.balance_game_share_save_success))
    val imageSaveFailureMessage =
        rememberUpdatedState(stringResource(Res.string.balance_game_share_save_failure))
    val imageShareFailureMessage =
        rememberUpdatedState(stringResource(Res.string.balance_game_share_share_failure))
    val galleryPermissionRequiredMessage =
        rememberUpdatedState(stringResource(Res.string.balance_game_share_gallery_permission_required))

    BindEffect(permissionsController = permissionsController)

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is BalanceGameShareSideEffect.NavigateToBack -> navigateToBack()
                is BalanceGameShareSideEffect.ShowSnackBar -> {
                    val message =
                        when (sideEffect) {
                            is BalanceGameShareSideEffect.ShowSnackBar.ImageSaveSuccess -> imageSaveSuccessMessage.value
                            is BalanceGameShareSideEffect.ShowSnackBar.ImageSaveFailure -> imageSaveFailureMessage.value
                            is BalanceGameShareSideEffect.ShowSnackBar.ImageShareFailure -> imageShareFailureMessage.value
                            is BalanceGameShareSideEffect.ShowSnackBar.GalleryPermissionRequired ->
                                galleryPermissionRequiredMessage.value
                        }
                    showSnackbarMessage(
                        snackbarHostState = snackbarHostState,
                        coroutineScope = this,
                        message = message,
                    )
                }
            }
        }
    }

    BalanceGameShareScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                is BalanceGameShareIntent.ClickSaveImage ->
                    coroutineScope.launch {
                        if (!requiresLegacyGalleryWritePermission()) {
                            viewModel.intent(intent)
                            return@launch
                        }

                        try {
                            permissionsController.providePermission(Permission.WRITE_STORAGE)
                            viewModel.intent(intent)
                        } catch (_: DeniedAlwaysException) {
                            viewModel.intent(BalanceGameShareIntent.SaveImagePermissionDenied)
                        } catch (_: DeniedException) {
                            viewModel.intent(BalanceGameShareIntent.SaveImagePermissionDenied)
                        } catch (_: RequestCanceledException) {
                            viewModel.intent(BalanceGameShareIntent.SaveImagePermissionDenied)
                        }
                    }

                else -> viewModel.intent(intent)
            }
        },
    )
}
