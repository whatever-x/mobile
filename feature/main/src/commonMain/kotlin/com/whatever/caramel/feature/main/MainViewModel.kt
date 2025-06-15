package com.whatever.caramel.feature.main

import androidx.lifecycle.SavedStateHandle
import com.whatever.caramel.core.domain.usecase.user.UpdateUserSettingUseCase
import com.whatever.caramel.core.firebaseMessaging.FcmTokenProvider
import com.whatever.caramel.core.viewmodel.BaseViewModel
import com.whatever.caramel.feature.main.mvi.MainIntent
import com.whatever.caramel.feature.main.mvi.MainSideEffect
import com.whatever.caramel.feature.main.mvi.MainState
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION

class MainViewModel(
    private val updateUserSettingUseCase: UpdateUserSettingUseCase,
    private val fcmTokenProvider: FcmTokenProvider,
    private val permissionsController: PermissionsController,
    savedStateHandle: SavedStateHandle,
): BaseViewModel<MainState, MainSideEffect, MainIntent>(savedStateHandle) {

    override fun handleClientException(throwable: Throwable) {
        super.handleClientException(throwable)

        TODO()
    }

    override fun createInitialState(savedStateHandle: SavedStateHandle): MainState {
        return MainState
    }

    override suspend fun handleIntent(intent: MainIntent) {
        TODO()
    }

    fun updateFcmToken() {
        launch {
            fcmTokenProvider.updateToken()
        }
    }

    fun updateUserSetting() {
        launch {
            updateUserSettingUseCase(
                notificationEnabled = permissionsController.isPermissionGranted(
                    permission = Permission.REMOTE_NOTIFICATION
                )
            )
        }
    }

}