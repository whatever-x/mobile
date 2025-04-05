package com.whatever.caramel.feature.copule.invite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalClipboard
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.designsystem.components.LocalSnackbarHostState
import com.whatever.caramel.core.designsystem.components.showSnackbarMessage
import com.whatever.caramel.feature.copule.invite.clipboard.createPlatformClipEntry
import com.whatever.caramel.feature.copule.invite.mvi.CoupleInviteSideEffect
import com.whatever.caramel.feature.copule.invite.share.ShareService
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CoupleInviteRoute(
    viewModel: CoupleInviteViewModel = koinViewModel(),
    shareService: ShareService = getKoin().get(),
    navigateToConnectCouple: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val clipboard = LocalClipboard.current
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is CoupleInviteSideEffect.NavigateToConnectCouple -> navigateToConnectCouple()
                is CoupleInviteSideEffect.NavigateToLogin -> navigateToLogin()
                is CoupleInviteSideEffect.CopyToClipBoardWithShowSnackBar -> {
                    showSnackbarMessage(
                        snackbarHostState = snackbarHostState,
                        coroutineScope = scope,
                        message = "초대 코드를 복사했습니다"
                    )
                    clipboard.setClipEntry(
                        clipEntry = createPlatformClipEntry(
                            inviteCode = sideEffect.inviteCode
                        )
                    )
                }
                is CoupleInviteSideEffect.ShareOfInvite -> {
                    shareService.shareContents(
                        title = "타이틀 입니다.",
                        url = "https://어쩌구저쩌구.com"
                    )
                }
            }
        }
    }

    CoupleInviteScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) }
    )
}
