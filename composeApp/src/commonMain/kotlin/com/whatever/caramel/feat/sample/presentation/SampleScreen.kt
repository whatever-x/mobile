package com.whatever.caramel.feat.sample.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.whatever.caramel.core.analytics.getCaramelAnalytics
import com.whatever.caramel.core.crashlytics.getCaramelCrashlytics
import com.whatever.caramel.feat.sample.presentation.mvi.SampleIntent
import com.whatever.caramel.feat.sample.presentation.mvi.SampleSideEffect
import com.whatever.caramel.feat.sample.presentation.mvi.SampleUiState
import io.github.aakira.napier.Napier
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SampleRoute(
    viewModel: SampleViewModel = koinViewModel()
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getSampleData()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SampleSideEffect.ShowSnackBar -> {
                    // @ham2174 EX : 스낵바 호출, 네비게이션 이동 등 다른 사이드 이펙트 처리
                    Napier.d { "스낵바 호출" }
                }
            }
        }
    }

    FirebaseSampleScreen(
        state = uiState.value,
        onIntent = { intent -> viewModel.intent(intent)}
    )
}

@Composable
private fun SampleScreen(
    state: SampleUiState,
    onIntent: (SampleIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = state.text,
            fontSize = 12.sp,
            color = Color.Black
        )

        Button(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onClick = { onIntent(SampleIntent.ClickButton) }
        ) {
            Text(
                text = "Intent 테스트 버튼"
            )
        }
    }
}

@Composable
private fun FirebaseSampleScreen(
    state: SampleUiState,
    onIntent: (SampleIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = state.text,
            fontSize = 12.sp,
            color = Color.Black
        )

        Column (
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ){
            Button(
                onClick = {
                    onIntent(SampleIntent.ClickButton)
                    getCaramelCrashlytics().sendCrashInfo(
                        userId = "test_user_1",
                        log = "test_log",
                        keys = mapOf(
                            "test_key" to "test_value"
                        )
                    )
                    throw IllegalArgumentException("test crash")
                }
            ) {
                Text(
                    text = "샘플 크래시 버튼"
                )
            }

            Button(
                onClick = {
                    onIntent(SampleIntent.ClickButton)
                    getCaramelAnalytics().logEvent(
                        eventName = "Button Click",
                        params = mapOf(
                            "screen" to "sample"
                        )
                    )
                    Napier.d { "Start Analytics logEvent" }
                }
            ) {
                Text(
                    text = "Analytics 버튼"
                )
            }
        }
    }
}