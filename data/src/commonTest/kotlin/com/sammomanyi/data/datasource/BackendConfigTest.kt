package com.sammomanyi.data.datasource

import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class BackendConfigTest {

    @Test
    fun customConfig_usesLegacyRestRoutes() {
        val config = BackendConfig.custom("http://localhost:8080/")

        assertEquals("http://localhost:8080/auth/login", config.signInUrl)
        assertEquals("http://localhost:8080/auth/register", config.registerUrl)
        assertEquals("http://localhost:8080/listings", config.listingsUrl)
        assertEquals("http://localhost:8080/bookings", config.bookingsUrl)
        assertEquals("http://localhost:8080/bookings/check-availability", config.checkAvailabilityUrl)
        assertEquals("http://localhost:8080/payments/intent", config.paymentIntentUrl)
    }

    @Test
    fun supabaseConfig_usesEdgeFunctionRoutesAndApiKey() = runTest {
        val config = BackendConfig.supabase(
            projectUrl = "https://demo-project.supabase.co/",
            anonKey = "anon-key"
        )
        val builder = HttpRequestBuilder()

        builder.applyAuthorizedHeaders(config, FakeCacheDataSource(token = "session-token"))

        assertEquals("https://demo-project.supabase.co/functions/v1/auth-login", config.signInUrl)
        assertEquals("https://demo-project.supabase.co/functions/v1/listings", config.listingsUrl)
        assertEquals("anon-key", builder.headers["apikey"])
        assertEquals("Bearer session-token", builder.headers["Authorization"])
    }

    private class FakeCacheDataSource(
        private val token: String
    ) : CacheDataSource {
        override suspend fun getAuthToken(): String = token

        override suspend fun saveAuthToken(token: String) = Unit

        override suspend fun clearAuthToken() = Unit
    }
}
