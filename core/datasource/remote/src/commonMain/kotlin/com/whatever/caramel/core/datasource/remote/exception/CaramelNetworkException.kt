package com.whatever.caramel.core.datasource.remote.exception

data class CaramelNetworkException(
    val code: String,
    val debugMessage: String?,
    override val message: String,
    val description: String?,
    val errorUiType: String,
) : Exception(message)