package com.whatever.caramel.feat.sample.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
                is SampleSideEffect.ShowError -> {
                    Napier.e { "오류발생 = ${sideEffect.message}" }
                }
            }
        }
    }

    /*
    * @RyuSw-cs 2025.02.25
    * SampleScreen - 네트워크 통신 불러오는 Screen
    * LocalSampleScreen - 네트워크 + 로컬 데이터 불러오는 Screen
    * */
    LocalSampleScreen(
        state = uiState.value,
        onIntent = { intent -> viewModel.intent(intent) }
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
            text = state.networkResult,
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
private fun LocalSampleScreen(
    state: SampleUiState,
    onIntent: (SampleIntent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Network")
        Text(
            text = state.networkResult,
            fontSize = 12.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text("Local")
        Text(
            text = state.localResult,
            fontSize = 12.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Row {
            Button(modifier = Modifier.weight(1f), onClick = {
                onIntent.invoke(SampleIntent.ClickButton)
            }) {
                Text("intent test")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(modifier = Modifier.weight(1f), onClick = {
                if(state.networkResultData != null){
                    onIntent.invoke(SampleIntent.SaveLocal(state.networkResultData))
                }
            }) {
                Text("save local")
            }
            Spacer(modifier = Modifier.padding(16.dp))
            Button(modifier = Modifier.weight(1f), onClick = {
                onIntent.invoke(SampleIntent.GetLocal)
            }) {
                Text("get local")
            }
        }
    }
}