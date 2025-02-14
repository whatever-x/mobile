package com.whatever.caramel.feature.main.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.internal.BackHandler
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import com.whatever.caramel.feature.main.home.HomeTab

class EditorScreenRoot : Screen{
    @Composable
    override fun Content() {
        EditorScreen()
    }
}

@OptIn(InternalVoyagerApi::class)
@Composable
fun EditorScreen(modifier: Modifier = Modifier) {
    val tabNavigator = LocalTabNavigator.current

    BackHandler(true) {
        tabNavigator.current = HomeTab
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Editor",
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp
            ),
            modifier = Modifier
        )
    }
}