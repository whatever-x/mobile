package com.whatever.caramel.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.whatever.caramel.feat.sample.presentation.SampleRoute

@Composable
fun CaramelComposeApp(
    navHostController: NavHostController
) {
    MaterialTheme {
        CaramelNavHost(
            navHostController = navHostController
        )
    }
}

