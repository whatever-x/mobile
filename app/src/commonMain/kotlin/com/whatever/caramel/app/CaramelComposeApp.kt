package com.whatever.caramel.app

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
fun CaramelComposeApp(
    navHostController: NavHostController
) {
    CaramelTheme {
        val snackBarHostState = remember { SnackbarHostState() }

        CaramelScaffold(
            snackBarHost = {
                SnackbarHost(
                    hostState = snackBarHostState
                )
            },
        ) {
            CaramelNavHost(
                modifier = Modifier,
                navHostController = navHostController,
            )
        }
    }
}
