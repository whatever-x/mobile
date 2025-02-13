package com.whatever.caramel.feature.profile.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.internal.BackHandler
import com.whatever.caramel.app.Route

class ProfileNicknameScreenRoot : Screen {
    @Composable
    override fun Content() {
        ProfileNicknameScreen()
    }
}

@OptIn(InternalVoyagerApi::class)
@Composable
fun ProfileNicknameScreen(modifier: Modifier = Modifier) {
    val navigator = LocalNavigator.currentOrThrow
    val profileBirthScreen = rememberScreen(Route.ProfileBirth)
    var showDialog by remember { mutableStateOf(false) }

    BackHandler(true){
        showDialog = true
    }

    if(showDialog){
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text("Cancel")
            },
            text = {
                Text("Would you like to cancel membership?")
            },
            confirmButton = {
                Button(
                    content = {
                        Text("no")
                    },
                    onClick = {
                        showDialog = false
                    }
                )
            },
            dismissButton = {
                Button(
                    content = {
                        Text("yes")
                    },
                    onClick = {
                        showDialog = false
                        navigator.pop()
                    }
                )
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile nickname",
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp
            ),
            modifier = modifier
        )
        Spacer(modifier = modifier.padding(top = 16.dp))
        Button(
            content = {
                Text("profile birth")
            },
            onClick = {
                navigator.push(profileBirthScreen)
            }
        )
    }
}