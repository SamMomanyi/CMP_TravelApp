package com.codewithfk.travenor.di

import com.codewithfk.data.datasource.CacheDataSource
import com.codewithfk.travenor.cache.DataStoreCacheSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun cacheModule(): Module = module {

    single<CacheDataSource> {
        DataStoreCacheSource(get())
    }

}