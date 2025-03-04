package com.whatever.caramel.feat.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory

@Composable
fun PermissionScreen(modifier: Modifier = Modifier) {
    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) {
        factory.createPermissionsController()
    }
    BindEffect(controller)

    val viewModel = viewModel {
        PermissionViewModel(controller)
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        when(viewModel.state) {
            PermissionState.Granted -> {
                Text(text = "Granted")
            }
            PermissionState.DeniedAlways -> {
                Text(text = "DeniedAlways")
                Button(
                    content = {
                        Text(text = "Setting")
                    },
                    onClick = {
                        controller.openAppSettings()
                    }
                )
            }
            else -> {
                Text("Denied")
                Button(
                    content = {
                        Text("Request")
                    },
                    onClick = {
                        viewModel.providerOrRequestCameraPermission()
                    }
                )
            }
        }
    }
}