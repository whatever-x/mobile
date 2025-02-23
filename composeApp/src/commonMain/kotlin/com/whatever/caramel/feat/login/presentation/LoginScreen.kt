package com.whatever.caramel.feat.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whatever.caramel.feat.login.presentation.mvi.LoginIntent
import com.whatever.caramel.feat.login.presentation.mvi.LoginState

@Composable
internal fun LoginScreen(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "로그인 화면입니다.",
            fontSize = 32.sp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(space = 5.dp)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onIntent(LoginIntent.ClickKakaoLoginButton) }
            ) {
                Text(
                    text = "카카오 로그인",
                    fontSize = 18.sp
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onIntent(LoginIntent.ClickAppleLoginButton) }
            ) {
                Text(
                    text = "애플 로그인",
                    fontSize = 18.sp
                )
            }
        }
    }
}