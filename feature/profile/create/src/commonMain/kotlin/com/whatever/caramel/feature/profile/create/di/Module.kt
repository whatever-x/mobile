package com.whatever.caramel.feature.profile.create.di

import com.whatever.caramel.feature.profile.create.ProfileCreateViewModel
import dev.icerock.moko.permissions.PermissionsController
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileCreateFeatureModule =
    module {
        viewModel { (permissionsController: PermissionsController) ->
            ProfileCreateViewModel(
                savedStateHandle = get(),
                createUserProfileUseCase = get(),
                updateUserSettingUseCase = get(),
                permissionsController = permissionsController,
                crashlytics = get(),
            )
        }
    }
