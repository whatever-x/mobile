package com.whatever.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform