package com.whatever.caramel.feature.login

import androidx.compose.foundation.background
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
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.whatever.caramel.app.Route

class LoginScreenRoot : Screen {
    @Composable
    override fun Content() {
        LoginScreen()
    }
}

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val navigator = LocalNavigator.currentOrThrow
    val profileNickNameScreen = rememberScreen(Route.ProfileNickName)
    val homeScreen = rememberScreen(Route.Home)
    val coupleInviteScreen = rememberScreen(Route.CoupleInvite)

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp
            ),
            modifier = modifier
        )
        Spacer(modifier = modifier.padding(top = 16.dp))

        Button(
            content = {
                Text("Home")
            },
            onClick = {
                navigator.replace(homeScreen)
            }
        )
        Spacer(modifier = modifier.padding(top = 16.dp))
        Button(
            content = {
                Text("profile nickname")
            },
            onClick = {
                navigator.push(profileNickNameScreen)
            }
        )
        Spacer(modifier = modifier.padding(top = 16.dp))
        Button(
            content = {
                Text("couple invite")
            },
            onClick = {
                navigator.replace(coupleInviteScreen)
            }
        )
    }
}
