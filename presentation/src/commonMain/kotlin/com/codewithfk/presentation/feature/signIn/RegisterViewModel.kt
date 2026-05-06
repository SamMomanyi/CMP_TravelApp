package com.codewithfk.presentation.feature.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.domain.model.RegisterModel
import com.codewithfk.domain.usecase.RegisterUseCase
import com.codewithfk.domain.usecase.SignInUseCase
import com.codewithfk.presentation.feature.register.AuthNavigation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _navigationState = MutableSharedFlow<AuthNavigation>()
    val navigationState = _navigationState.asSharedFlow()
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun onNameChange(newName: String) {
        _name.value = newName
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        _confirmPassword.value = newConfirmPassword
    }

    fun register() {
        viewModelScope.launch {
            if (_password.value != _confirmPassword.value) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Password and Confirm Password do not match"
                )
                return@launch
            }

            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = registerUseCase.execute(
                RegisterModel(
                    firstName = name.value,
                    email = email.value,
                    password = password.value,
                )
            )
            result.onSuccess { user ->
                _uiState.value = _uiState.value.copy(user = user, isLoading = false)
                _navigationState.emit(AuthNavigation.ToListing)
            }.onFailure { error ->
                _uiState.value =
                    _uiState.value.copy(errorMessage = error.message, isLoading = false)
            }
        }
    }

    fun navigateToLogin() {
        viewModelScope.launch {
            _navigationState.emit(AuthNavigation.ToLogin)
        }
    }

    fun removeErrorMessage() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

}
