package com.whatever.caramel.core.remote.network.config

actual fun currentTimeZone(): String =
    java.util.TimeZone.getDefault().id