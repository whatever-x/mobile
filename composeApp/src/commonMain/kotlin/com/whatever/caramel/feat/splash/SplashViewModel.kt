package com.whatever.caramel.feat.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _state : MutableStateFlow<SplashState> = MutableStateFlow(SplashState())
    val state = _state
        .onStart {
            checkAppStateData(
                needLogin = false,
                needPermission = false,
                isFirst = true,
                needCouple = false,
                needProfile = false,
                normal = false
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SplashState()
        )

    // @RyuSw-cs : 2025.02.12 샘플 State 설정 (필요한 flag를 설정)
    private fun checkAppStateData(
        needLogin : Boolean,
        needPermission : Boolean,
        isFirst : Boolean,
        needProfile : Boolean,
        needCouple : Boolean,
        normal : Boolean
    ) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            delay(1000L)
            _state.update {
                it.copy(
                    isLoading = false,
                    needPermission = needPermission,
                    needLogin = needLogin,
                    needProfile = needProfile,
                    needCouple = needCouple,
                    isFirst = isFirst,
                    isSuccess = normal
                )
            }
        }
    }
}