package com.sammomanyi.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sammomanyi.cache.createDataStore
import com.sammomanyi.cache.dataStoreFileName
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun platformModule(): Module = module {
    single<String> { "http://localhost:8080" }

    single <DataStore<Preferences>>{
        createDataStore(producerPath = { "${getDocumentPath()}/${dataStoreFileName}" })
    }
}


@OptIn(ExperimentalForeignApi::class)
fun getDocumentPath(): String {
    val documentPath = NSFileManager.defaultManager.URLForDirectory(
        directory =  NSDocumentDirectory,
        inDomain =  NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    return requireNotNull(documentPath!!.path)
}