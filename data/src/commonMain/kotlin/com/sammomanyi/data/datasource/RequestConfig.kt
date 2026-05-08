package com.sammomanyi.data.datasource

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header

internal suspend fun HttpRequestBuilder.applyAuthorizedHeaders(
    backendConfig: BackendConfig,
    cacheDataSource: CacheDataSource
) {
    val token = cacheDataSource.getAuthToken()
    if (token.isNotBlank()) {
        header("Authorization", "${backendConfig.authTokenPrefix} $token")
    }
    applyPublicApiKey(backendConfig)
}

internal fun HttpRequestBuilder.applyPublicApiKey(backendConfig: BackendConfig) {
    backendConfig.publicApiKey
        ?.takeIf { it.isNotBlank() }
        ?.let { header("apikey", it) }
}
