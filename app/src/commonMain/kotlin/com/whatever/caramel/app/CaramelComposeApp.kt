package com.whatever.caramel.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.whatever.caramel.core.designsystem.components.CaramelSnackBarHost
import com.whatever.caramel.core.designsystem.components.CaramelSnackbar
import com.whatever.caramel.core.designsystem.components.LocalSnackbarHostState
import com.whatever.caramel.core.designsystem.themes.CaramelTheme

@Composable
fun CaramelComposeApp(
    navHostController: NavHostController
) {
    CaramelTheme {
        val snackBarHostState = remember { SnackbarHostState() }

        CompositionLocalProvider(
            LocalSnackbarHostState provides snackBarHostState
        ) {
            CaramelScaffold(
                snackBarHost = {
                    CaramelSnackBarHost(
                        modifier = Modifier.padding(bottom = 20.dp),
                        hostState = LocalSnackbarHostState.current,
                        snackbar = { snackbarData ->
                            CaramelSnackbar(
                                snackbarData = snackbarData
                            )
                        },
                    )
                },
            ) {
                CaramelNavHost(
                    modifier = Modifier,
                    navHostController = navHostController
                )
            }
        }
    }
}
