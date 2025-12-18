package com.whatever.caramel.external.datastore.di

import com.whatever.caramel.external.datastore.createDataStore
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDataStoreModule: Module
    get() =
        module {
            single { createDataStore(context = androidApplication()) }
        }
