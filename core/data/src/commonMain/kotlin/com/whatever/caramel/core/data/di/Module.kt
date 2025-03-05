package com.whatever.caramel.core.data.di

import com.whatever.caramel.core.data.database.di.databaseModule
import com.whatever.caramel.core.data.datastore.di.localDataSourceModule
import com.whatever.caramel.core.data.remote.di.networkClientModule
import com.whatever.caramel.core.data.remote.network.HttpClientFactory
import com.whatever.caramel.core.data.repository.SampleRepositoryImpl
import com.whatever.caramel.core.domain.repository.SampleRepository
import org.koin.dsl.module

val networkModule = module {
    includes(networkClientModule)
    single {
        HttpClientFactory.create(
            engine = get()
        )
    }
}

val repositoryModule = module {
    includes(databaseModule, localDataSourceModule)
    single<SampleRepository> {
        SampleRepositoryImpl(
            remoteSampleDataSource = get(),
            localSampleDataSource = get(),
            sampleDao = get()
        )
    }
}