package com.sammomanyi.domain.di

import com.sammomanyi.domain.repository.BookingRepository
import com.sammomanyi.domain.repository.CacheRepository
import com.sammomanyi.domain.repository.ListingRepository
import com.sammomanyi.domain.repository.PaymentRepository
import com.sammomanyi.domain.repository.UserRepository
import com.sammomanyi.domain.usecase.CheckAvailabilityUseCase
import com.sammomanyi.domain.usecase.CreateBookingUseCase
import com.sammomanyi.domain.usecase.CreatePaymentIntentUseCase
import com.sammomanyi.domain.usecase.GetAllBookingUseCase
import com.sammomanyi.domain.usecase.GetAllListingUseCase
import com.sammomanyi.domain.usecase.GetAuthTokenUseCase
import com.sammomanyi.domain.usecase.GetListingByIdUseCase
import com.sammomanyi.domain.usecase.RegisterUseCase
import com.sammomanyi.domain.usecase.RemoveAuthTokenUseCase
import com.sammomanyi.domain.usecase.SignInUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GetAllListingUseCase(get<ListingRepository>())
    }
    factory {
        SignInUseCase(get<UserRepository>())
    }
    factory {
        RegisterUseCase(get<UserRepository>())
    }

    factory {
        GetAuthTokenUseCase(get<CacheRepository>())
    }
    factory {
        GetListingByIdUseCase(get<ListingRepository>())
    }
    factory {
        CheckAvailabilityUseCase(get<BookingRepository>())
    }
    factory {
        CreateBookingUseCase(get<BookingRepository>())
    }
    factory {
        CreatePaymentIntentUseCase(get<PaymentRepository>())
    }
    factory {
        GetAllBookingUseCase(get<BookingRepository>())
    }
    factory {
        RemoveAuthTokenUseCase(get())
    }
}