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
    showErrorDialog: (String, String?) -> Unit,
    showErrorToast: (String) -> Unit,
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
                        message = "초대 코드를 복사했습니다",
                    )
                    clipboard.setClipEntry(
                        clipEntry =
                            createPlatformClipEntry(
                                inviteCode = sideEffect.inviteCode,
                            ),
                    )
                }
                is CoupleInviteSideEffect.ShareOfInvite -> {
                    shareService.shareContents(
                        title = "연인이 카라멜에 초대했어요!\n초대를 수락하고 일정과 메모를 공유해보세요",
                        url = "https://caramel.onelink.me/7nAT/2l5wk4ab?p1=${sideEffect.inviteCode}",
                    )
                }
                is CoupleInviteSideEffect.ShowErrorDialog -> showErrorDialog(sideEffect.message, sideEffect.description)
                is CoupleInviteSideEffect.ShowErrorToast -> showErrorToast(sideEffect.message)
            }
        }
    }

    CoupleInviteScreen(
        state = state,
        onIntent = { intent -> viewModel.intent(intent) },
    )
}
