package com.sammomanyi.di

import android.content.Context
import com.sammomanyi.cache.createDataStore
import com.sammomanyi.cache.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<String> { "http://10.0.2.2:8080" }

    single {
        createDataStore(
            producerPath = {
                get<Context>().filesDir.resolve(dataStoreFileName).absolutePath
            }
        )
    }
}