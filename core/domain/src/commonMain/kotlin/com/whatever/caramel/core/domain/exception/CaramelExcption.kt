package com.whatever.caramel.core.domain.exception

data class CaramelException(
    override val message: String,
    val debugMessage: String?,
    val errorUiType: ErrorUiType
): Exception(message)

enum class ErrorUiType {
    SNACK_BAR,
    EMPTY_UI,
    ;
}