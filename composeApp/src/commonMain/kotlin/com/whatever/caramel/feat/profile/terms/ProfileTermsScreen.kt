package com.whatever.caramel.feat.profile.terms

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

class ProfileTermsScreenRoot : Screen{
    @Composable
    override fun Content() {
        ProfileTermsScreen()
    }
}

@Composable
fun ProfileTermsScreen(modifier: Modifier = Modifier) {
    val navigator = LocalNavigator.currentOrThrow
    val coupleInviteScreen = rememberScreen(Route.CoupleInvite)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile terms",
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp
            ),
            modifier = modifier
        )
        Spacer(modifier = modifier.padding(top = 16.dp))
        Button(
            content =  {
                Text("couple")
            },
            onClick = {
                navigator.popUntilRoot()
                navigator.replace(coupleInviteScreen)
            }
        )
    }
}