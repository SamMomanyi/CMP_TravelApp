package com.codewithfk.presentation.feature.signIn

import com.codewithfk.domain.model.UserModel


data class RegisterUiState(
    val user: UserModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)