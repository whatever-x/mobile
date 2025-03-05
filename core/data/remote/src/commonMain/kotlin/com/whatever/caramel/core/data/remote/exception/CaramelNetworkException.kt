package com.whatever.caramel.core.data.remote.exception


data class CaramelNetworkException(
    val code: String,
    val debugMessage: String?,
    override val message: String,
) : Exception(message)