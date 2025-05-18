package com.whatever.caramel.core.deeplink

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun getDeepLinkHandler(): DeepLinkHandler = object : KoinComponent {}.get()