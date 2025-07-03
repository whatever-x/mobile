package com.whatever.caramel.core.domain.exception

data class CaramelException(
    val code: String,
    val debugMessage: String? = null,
    override val message: String,
    val description: String? = null,
    val errorUiType: ErrorUiType,
) : Exception(message)
