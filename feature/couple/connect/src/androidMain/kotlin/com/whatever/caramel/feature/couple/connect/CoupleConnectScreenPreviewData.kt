package com.whatever.caramel.feature.couple.connect

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.whatever.caramel.feature.couple.connect.mvi.CoupleConnectState

internal class CoupleConnectScreenPreviewData :
    PreviewParameterProvider<CoupleConnectState> {
    override val values: Sequence<CoupleConnectState> =
        sequenceOf(
            CoupleConnectState(
                invitationCode = "코드값이 있을때",
            ),
            CoupleConnectState(
                invitationCode = "",
            ),
        )
}
