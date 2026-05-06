package com.codewithfk.presentation.feature.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.domain.usecase.GetAuthTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(private val useCase: GetAuthTokenUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow<AppUiState>(AppUiState())
    val state = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadAuthToken()
        }
    }

    suspend fun loadAuthToken() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        val token = useCase.execute()
        if (token.isFailure) {
            _uiState.value = _uiState.value.copy(isLoading = false, authToken = null)
        } else {
            _uiState.value = _uiState.value.copy(isLoading = false, authToken = token.getOrNull()!!)

        }
    }
}