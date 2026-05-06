package com.codewithfk.presentation.feature.register

import com.codewithfk.domain.model.UserModel


data class SignInUiState(
    val user: UserModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)