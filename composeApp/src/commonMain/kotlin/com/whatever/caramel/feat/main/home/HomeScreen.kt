package com.whatever.caramel.feat.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.whatever.caramel.feat.temp.SampleBottomSheet

class HomeScreenRoot : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        BottomSheetNavigator (
            hideOnBackPress = false
        ) {
            HomeScreen()
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val bottomSheetNavigator = LocalBottomSheetNavigator.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Home",
            style = TextStyle(
                color = Color.Black,
                fontSize = 24.sp
            ),
            modifier = Modifier
        )
        Spacer(modifier = modifier.padding(16.dp))
        Button(
            onClick = {
                bottomSheetNavigator.show(SampleBottomSheet())
            }
        ){
            Text("show bottom sheet")
        }
    }
}