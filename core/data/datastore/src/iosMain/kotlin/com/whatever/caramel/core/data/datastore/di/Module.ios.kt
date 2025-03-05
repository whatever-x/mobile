package com.whatever.caramel.core.data.datastore.di

import com.whatever.caramel.core.data.datastore.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformDataStoreModule: Module
    get() = module {
        single { createDataStore() }
    }