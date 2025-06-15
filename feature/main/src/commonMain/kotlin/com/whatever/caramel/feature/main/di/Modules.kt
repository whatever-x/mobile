package com.whatever.caramel.feature.main.di

import com.whatever.caramel.feature.main.MainViewModel
import dev.icerock.moko.permissions.PermissionsController
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { (permissionsController: PermissionsController) ->
        MainViewModel(
            savedStateHandle = get(),
            updateUserSettingUseCase = get(),
            fcmTokenProvider = get(),
            permissionsController = permissionsController,
        )
    }
}