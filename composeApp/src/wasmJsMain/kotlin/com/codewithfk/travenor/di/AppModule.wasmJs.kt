package com.codewithfk.travenor.di

import com.codewithfk.data.datasource.CacheDataSource
import com.codewithfk.travenor.cache.InMemoryCacheSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun cacheModule(): Module = module {
    single<CacheDataSource> {
        InMemoryCacheSource()
    }
}

actual fun platformModule(): Module  = module {
    single<String> { "http://localhost:8080" }
}