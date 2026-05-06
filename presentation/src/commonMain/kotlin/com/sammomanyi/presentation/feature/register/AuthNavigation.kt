package com.sammomanyi.presentation.feature.register

sealed class AuthNavigation {
    object ToLogin : AuthNavigation()
    object ToSignUp : AuthNavigation()
    object ToListing : AuthNavigation()
}