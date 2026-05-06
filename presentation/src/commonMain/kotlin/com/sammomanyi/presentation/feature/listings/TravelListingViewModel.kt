package com.sammomanyi.presentation.feature.listings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sammomanyi.domain.AppError
import com.sammomanyi.domain.usecase.GetAllListingUseCase
import com.sammomanyi.domain.usecase.RemoveAuthTokenUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TravelListingViewModel(val getAllListingUseCase: GetAllListingUseCase,
    val removeAuthTokenUseCase: RemoveAuthTokenUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(TravelListingUiState())
    val state = _uiState.asStateFlow()

    private val _navigationState = MutableSharedFlow<TravelListingNavigation>()
    val navigationState = _navigationState.asSharedFlow()

    init {
        loadTravelListings()
    }

    fun loadTravelListings() {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(isLoading = true, errorMessage = null, listings = emptyList())
            try {
                getAllListingUseCase.execute().let { listings ->
                    if (listings.isFailure) {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = listings.exceptionOrNull() as AppError
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(
                            listings = listings.getOrNull()!!,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = AppError.Unknown()
                )
            }
        }
    }

    fun handleError() {
        when (state.value.errorMessage) {
            is AppError.Unauthorized -> {
                viewModelScope.launch {
                    _uiState.value = state.value.copy(errorMessage = null)
                    removeAuthTokenUseCase.execute()
                    _navigationState.emit(TravelListingNavigation.GoToLogin)
                }
            }
            else -> {
                loadTravelListings()
            }
        }
    }

}