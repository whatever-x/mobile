package com.whatever.caramel.core.datastore.di

import com.whatever.caramel.core.datastore.createDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDataStoreModule: Module
    get() =
        module {
            single { createDataStore(androidApplication()) }
        }
