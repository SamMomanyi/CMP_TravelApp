package com.codewithfk.domain.di

import com.codewithfk.domain.repository.BookingRepository
import com.codewithfk.domain.repository.CacheRepository
import com.codewithfk.domain.repository.ListingRepository
import com.codewithfk.domain.repository.PaymentRepository
import com.codewithfk.domain.repository.UserRepository
import com.codewithfk.domain.usecase.CheckAvailabilityUseCase
import com.codewithfk.domain.usecase.CreateBookingUseCase
import com.codewithfk.domain.usecase.CreatePaymentIntentUseCase
import com.codewithfk.domain.usecase.GetAllBookingUseCase
import com.codewithfk.domain.usecase.GetAllListingUseCase
import com.codewithfk.domain.usecase.GetAuthTokenUseCase
import com.codewithfk.domain.usecase.GetListingByIdUseCase
import com.codewithfk.domain.usecase.RegisterUseCase
import com.codewithfk.domain.usecase.RemoveAuthTokenUseCase
import com.codewithfk.domain.usecase.SignInUseCase
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