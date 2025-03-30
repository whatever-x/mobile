package com.whatever.caramel.core.domain.exception

data class CaramelException(
    val code: String,
    override val message: String,
    val debugMessage: String? =  null
) : Exception(message)