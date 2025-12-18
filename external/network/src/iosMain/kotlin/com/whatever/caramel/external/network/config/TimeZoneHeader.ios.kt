package com.whatever.caramel.external.network.config

import platform.Foundation.NSTimeZone
import platform.Foundation.localTimeZone

actual fun currentTimeZone(): String = NSTimeZone.localTimeZone.name
