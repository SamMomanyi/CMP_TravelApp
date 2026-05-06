package com.sammomanyi.presentation.feature.signIn

import com.sammomanyi.domain.model.UserModel


data class RegisterUiState(
    val user: UserModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)