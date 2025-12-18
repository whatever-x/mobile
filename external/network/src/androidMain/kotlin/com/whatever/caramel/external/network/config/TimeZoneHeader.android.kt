package com.whatever.caramel.external.network.config

import java.util.TimeZone

actual fun currentTimeZone(): String =
    TimeZone
        .getDefault()
        .id
