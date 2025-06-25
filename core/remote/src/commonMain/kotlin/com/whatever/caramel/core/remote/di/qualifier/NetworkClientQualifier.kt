package com.whatever.caramel.core.remote.di.qualifier

import org.koin.core.qualifier.named

val SampleClient = named("SampleClient")
val AuthClient = named("AuthClient")
val DefaultClient = named("DefaultClient")