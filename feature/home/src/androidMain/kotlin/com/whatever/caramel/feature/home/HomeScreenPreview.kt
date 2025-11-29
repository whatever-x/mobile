package com.whatever.caramel.feature.home

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.core.ui.admob.GoogleAdBannerType
import com.whatever.caramel.core.ui.admob.GoogleAdUnitIds
import com.whatever.caramel.core.ui.admob.LocalHomeGoogleAdBanner
import com.whatever.caramel.core.ui.admob.rememberGoogleAdBannerState
import com.whatever.caramel.feature.home.mvi.HomeState

@RequiresPermission(Manifest.permission.INTERNET)
@Preview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewData::class) data: HomeState,
) {
    CaramelTheme {
        val homeBannerState = rememberGoogleAdBannerState(
            bannerType = GoogleAdBannerType.AnchoredAdaptive(),
            adUnitId = GoogleAdUnitIds.TEST_BANNER,
        )

        CompositionLocalProvider(
            LocalHomeGoogleAdBanner provides homeBannerState
        ) {
            HomeScreen(
                state = data,
                onIntent = {},
            )
        }
    }
}

@RequiresPermission(Manifest.permission.INTERNET)
@Preview
@Composable
private fun QuizPreview(
    @PreviewParameter(QuizPreviewData::class) quizType: HomeState,
) {
    CaramelTheme {
        val homeBannerState = rememberGoogleAdBannerState(
            bannerType = GoogleAdBannerType.AnchoredAdaptive(),
            adUnitId = GoogleAdUnitIds.TEST_BANNER,
        )

        CompositionLocalProvider(
            LocalHomeGoogleAdBanner provides homeBannerState
        ) {
            HomeScreen(
                state = quizType,
                onIntent = {},
            )
        }
    }
}
