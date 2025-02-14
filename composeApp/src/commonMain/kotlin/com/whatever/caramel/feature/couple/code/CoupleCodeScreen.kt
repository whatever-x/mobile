package com.whatever.caramel.feature.couple.code

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.whatever.caramel.app.Route

class CoupleCodeScreenRoot : Screen {
    @Composable
    override fun Content() {
        CoupleCodeScreen()
    }
}

@Composable
fun CoupleCodeScreen(modifier: Modifier = Modifier) {
    val navigator = LocalNavigator.currentOrThrow
    val mainScreen = rememberScreen(Route.Main)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Couple code",
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp
            ),
            modifier = modifier
        )
        Spacer(modifier = modifier.padding(top = 16.dp))
        Button(
            content = {
                Text("main")
            },
            onClick = {
                navigator.popUntilRoot()
                navigator.replace(mainScreen)
            }
        )
    }
}