# Backend migration notes

## Current state

This clone does not contain Firebase wiring.

The app currently talks to a custom HTTP backend through `RemoteDataSource` in [data/src/commonMain/kotlin/com/sammomanyi/data/datasource/RemoteDataSource.kt](../data/src/commonMain/kotlin/com/sammomanyi/data/datasource/RemoteDataSource.kt). The active platform bindings are:

- Android: [composeApp/src/androidMain/kotlin/com/sammomanyi/di/AppModule.android.kt](../composeApp/src/androidMain/kotlin/com/sammomanyi/di/AppModule.android.kt)
- iOS: [composeApp/src/iosMain/kotlin/com/sammomanyi/di/AppModule.ios.kt](../composeApp/src/iosMain/kotlin/com/sammomanyi/di/AppModule.ios.kt)

Both currently default to a local backend:

- Android: `http://10.0.2.2:8080`
- iOS: `http://localhost:8080`

## What changed

Backend wiring now goes through `BackendConfig`, which supports:

- `BackendConfig.custom(baseUrl)` for the current REST server
- `BackendConfig.supabase(projectUrl, anonKey)` for a Supabase-shaped setup

The Supabase mode is intentionally a placeholder contract, not a finished migration. It assumes you will preserve the app's existing request and response shapes by implementing Edge Functions such as:

- `auth-login`
- `auth-register`
- `listings`
- `bookings`
- `bookings-check-availability`
- `payments-intent`

This is the least disruptive bridge because the app still expects the current DTOs, including a login response shaped like `token + user`.

## Recommended next step

When you are ready to stand up Supabase, change the platform module from:

```kotlin
single<BackendConfig> { BackendConfig.custom("http://10.0.2.2:8080") }
```

to something like:

```kotlin
single<BackendConfig> {
    BackendConfig.supabase(
        projectUrl = "https://your-project.supabase.co",
        anonKey = "your-anon-key"
    )
}
```

Then implement the matching Edge Functions or adapt the client DTOs to native Supabase Auth and PostgREST responses.
