package com.sammomanyi.di

import com.sammomanyi.data.datasource.CacheDataSource
import com.sammomanyi.cache.DataStoreCacheSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun cacheModule(): Module = module {

    single<CacheDataSource> {
        DataStoreCacheSource(get())
    }

}