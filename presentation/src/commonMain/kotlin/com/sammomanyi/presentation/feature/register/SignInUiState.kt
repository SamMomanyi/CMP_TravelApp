package com.sammomanyi.presentation.feature.register

import com.sammomanyi.domain.model.UserModel


data class SignInUiState(
    val user: UserModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)