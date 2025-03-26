package com.whatever.caramel.core.domain.exception

data object NetworkExceptionCode {
    /* NETWORK(GLOBAL) */
    private const val NETWORK_GLOBAL_PREFIX = "GLOBAL"
    const val UNKNOWN = NETWORK_GLOBAL_PREFIX + "000"
}