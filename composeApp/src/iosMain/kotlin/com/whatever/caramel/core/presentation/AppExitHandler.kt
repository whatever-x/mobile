package com.whatever.caramel.core.presentation

import platform.posix.exit

actual fun closeApp() {
    exit(0)
}