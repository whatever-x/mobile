package com.whatever.caramel.core.remote.network.exception


data class CaramelNetworkException(
    val code: String,
    val debugMessage: String?,
    override val message: String,
) : Exception(message)