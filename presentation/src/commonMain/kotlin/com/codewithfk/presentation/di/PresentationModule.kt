package com.codewithfk.presentation.di

import com.codewithfk.domain.usecase.GetAllListingUseCase
import com.codewithfk.domain.usecase.GetAuthTokenUseCase
import com.codewithfk.domain.usecase.RegisterUseCase
import com.codewithfk.domain.usecase.SignInUseCase
import com.codewithfk.presentation.feature.app.AppViewModel
import com.codewithfk.presentation.feature.bookings.BookingListViewModel
import com.codewithfk.presentation.feature.checkout.CheckoutViewModel
import com.codewithfk.presentation.feature.details.TravelListingDetailsViewModel
import com.codewithfk.presentation.feature.listings.TravelListingViewModel
import com.codewithfk.presentation.feature.register.SignInViewModel
import com.codewithfk.presentation.feature.signIn.RegisterUiState
import com.codewithfk.presentation.feature.signIn.RegisterViewModel
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