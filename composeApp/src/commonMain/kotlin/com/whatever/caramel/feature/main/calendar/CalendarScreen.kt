package com.whatever.caramel.feature.main.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

class CalendarScreenRoot : Screen {
    @Composable
    override fun Content() {
        CalendarScreen()
    }
}

@Composable
fun CalendarScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calendar",
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp
            ),
            modifier = Modifier
        )
    }
}