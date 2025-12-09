@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.whatever.caramel.external.admob.banner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

/**
 * 광고 Unit Id 모음
 */
expect object GoogleAdUnitIds {
    val HOME_BANNER: String
}

/**
 * 구글 애드몹 - BannerView
 * @param modifier AndroidView / UIKitView 에 사용 되는 Modifier
 * @param bannerState 각 플랫폼에 사용되는 배너 상태 객체
 * (Android - AdView / iOS - GADBannerView)
 */
@Composable
internal expect fun GoogleAdBannerView(
    modifier: Modifier = Modifier,
    bannerState: GoogleAdBannerState,
)

/**
 * 플랫폼 별 배너 뷰 상태 객체
 *
 * Android - AdView
 *
 * iOS - GADBannerView
 */
expect class GoogleAdBannerState

/**
 * 구글 애드몹 배너 타입
 */
@Stable
sealed interface GoogleAdBannerType {
    /**
     * 앵커형 배너 - 화면 너비에 맞는 광고를 배치할 때 주로 사용
     * @constructor 전체 가용 너비 배너로 생성하려면 [FULL_WIDTH] 사용
     */
    data class AnchoredAdaptive(
        val width: Int = SCREEN_FULL_WIDTH,
    ) : GoogleAdBannerType {
        companion object {
            const val SCREEN_FULL_WIDTH = -1
        }
    }

    /**
     * 인라인형 배너 - 컨텐츠 혹은 리스트 사이에 광고를 배치 해야할 때 주로 사용
     */
    data class InlineAdaptive(
        val width: Int,
    ) : GoogleAdBannerType
}

/**
 * 구글 애드몹 배너 상태 객체 생성
 * @param adUnitId 광소 UnitId. [GoogleAdUnitIds] 참고
 * @param bannerType 배너 타입. [GoogleAdBannerType] 참고
 */
@Composable
expect fun rememberGoogleAdBannerState(
    adUnitId: String,
    bannerType: GoogleAdBannerType,
): GoogleAdBannerState
