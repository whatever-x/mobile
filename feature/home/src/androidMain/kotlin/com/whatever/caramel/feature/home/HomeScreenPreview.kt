package com.whatever.caramel.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.whatever.caramel.core.designsystem.themes.CaramelTheme
import com.whatever.caramel.external.admob.banner.GoogleAdBannerType
import com.whatever.caramel.external.admob.banner.GoogleAdUnitIds
import com.whatever.caramel.external.admob.banner.LocalHomeGoogleAdBanner
import com.whatever.caramel.external.admob.banner.rememberGoogleAdBannerState
import com.whatever.caramel.feature.home.mvi.HomeState

@Preview
@Composable
private fun HomeScreenPreview(
    @PreviewParameter(HomeScreenPreviewData::class) data: HomeState,
) {
    CaramelTheme {
        val homeBannerState = rememberGoogleAdBannerState(
            adUnitId = GoogleAdUnitIds.HOME_BANNER,
            bannerType = GoogleAdBannerType.AnchoredAdaptive()
        )

        CompositionLocalProvider(
            LocalHomeGoogleAdBanner provides homeBannerState,
        ) {
            HomeScreen(
                state = data,
                onIntent = {},
            )
        }
    }
}

@Preview
@Composable
private fun QuizPreview(
    @PreviewParameter(QuizPreviewData::class) quizType: HomeState,
) {
    CaramelTheme {
        val homeBannerState = rememberGoogleAdBannerState(
            adUnitId = GoogleAdUnitIds.HOME_BANNER,
            bannerType = GoogleAdBannerType.AnchoredAdaptive()
        )

        CompositionLocalProvider(
            LocalHomeGoogleAdBanner provides homeBannerState,
        ) {
            HomeScreen(
                state = quizType,
                onIntent = {},
            )
        }
    }
}
