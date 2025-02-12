package com.whatever.caramel.feature.splash

// @ RyuSw-cs : 2025.02.12 샘플 State
data class SplashState (
    val isLoading : Boolean = false,
    val isFirst : Boolean = false,
    val needPermission : Boolean = false,
    val needLogin : Boolean = false,
    val isSuccess : Boolean = false,
    val needProfile : Boolean = false,
    val needCouple : Boolean = false
)