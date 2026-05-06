package com.sammomanyi.presentation.di

import com.sammomanyi.domain.usecase.GetAllListingUseCase
import com.sammomanyi.domain.usecase.GetAuthTokenUseCase
import com.sammomanyi.domain.usecase.RegisterUseCase
import com.sammomanyi.domain.usecase.SignInUseCase
import com.sammomanyi.presentation.feature.app.AppViewModel
import com.sammomanyi.presentation.feature.bookings.BookingListViewModel
import com.sammomanyi.presentation.feature.checkout.CheckoutViewModel
import com.sammomanyi.presentation.feature.details.TravelListingDetailsViewModel
import com.sammomanyi.presentation.feature.listings.TravelListingViewModel
import com.sammomanyi.presentation.feature.register.SignInViewModel
import com.sammomanyi.presentation.feature.signIn.RegisterUiState
import com.sammomanyi.presentation.feature.signIn.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { TravelListingViewModel(get<GetAllListingUseCase>(),get()) }
    viewModel { SignInViewModel(get<SignInUseCase>()) }
    viewModel { RegisterViewModel(get<RegisterUseCase>()) }
    viewModel { (itemID: String) -> TravelListingDetailsViewModel(get(), itemID) }
    viewModel { (itemID: String) -> CheckoutViewModel(itemID, get(), get(), get(), get()) }
    viewModel { AppViewModel(get<GetAuthTokenUseCase>()) }
    viewModel { BookingListViewModel(get()) }
}