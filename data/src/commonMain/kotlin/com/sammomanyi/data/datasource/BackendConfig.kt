package com.sammomanyi.data.datasource

enum class BackendProvider {
    CUSTOM_API,
    SUPABASE
}

data class BackendRouteConfig(
    val signInPath: String,
    val registerPath: String,
    val listingsPath: String,
    val bookingsPath: String,
    val checkAvailabilityPath: String,
    val paymentIntentPath: String
)

data class BackendConfig(
    val provider: BackendProvider,
    val apiBaseUrl: String,
    val authBaseUrl: String = apiBaseUrl,
    val publicApiKey: String? = null,
    val authTokenPrefix: String = "Bearer",
    val routes: BackendRouteConfig
) {
    val signInUrl: String
        get() = authBaseUrl.withPath(routes.signInPath)

    val registerUrl: String
        get() = authBaseUrl.withPath(routes.registerPath)

    val listingsUrl: String
        get() = apiBaseUrl.withPath(routes.listingsPath)

    val bookingsUrl: String
        get() = apiBaseUrl.withPath(routes.bookingsPath)

    val checkAvailabilityUrl: String
        get() = apiBaseUrl.withPath(routes.checkAvailabilityPath)

    val paymentIntentUrl: String
        get() = apiBaseUrl.withPath(routes.paymentIntentPath)

    companion object {
        fun custom(baseUrl: String): BackendConfig {
            val routes = BackendRouteConfig(
                signInPath = "/auth/login",
                registerPath = "/auth/register",
                listingsPath = "/listings",
                bookingsPath = "/bookings",
                checkAvailabilityPath = "/bookings/check-availability",
                paymentIntentPath = "/payments/intent"
            )
            return BackendConfig(
                provider = BackendProvider.CUSTOM_API,
                apiBaseUrl = baseUrl,
                routes = routes
            )
        }

        fun supabase(projectUrl: String, anonKey: String): BackendConfig {
            val functionsBaseUrl = projectUrl.withPath("/functions/v1")
            val routes = BackendRouteConfig(
                signInPath = "/auth-login",
                registerPath = "/auth-register",
                listingsPath = "/listings",
                bookingsPath = "/bookings",
                checkAvailabilityPath = "/bookings-check-availability",
                paymentIntentPath = "/payments-intent"
            )
            return BackendConfig(
                provider = BackendProvider.SUPABASE,
                apiBaseUrl = functionsBaseUrl,
                authBaseUrl = functionsBaseUrl,
                publicApiKey = anonKey,
                routes = routes
            )
        }
    }
}

private fun String.withPath(path: String): String {
    val normalizedBase = trimEnd('/')
    val normalizedPath = if (path.startsWith('/')) path else "/$path"
    return normalizedBase + normalizedPath
}
