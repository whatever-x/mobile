package com.whatever.caramel.core.domain.vo.app

data class CheckForceUpdateResult(
    val requireUpdate: Boolean,
    val storeUri: String?,
)
