package com.sammomanyi.di

import com.sammomanyi.data.di.dataModule
import com.sammomanyi.domain.di.domainModule
import com.sammomanyi.presentation.di.presentationModule
import com.sammomanyi.payments.StripePaymentHandler
import org.koin.dsl.module

val stripeModule = module {
    single { StripePaymentHandler() }
}
val appModule = listOf(
    platformModule(), presentationModule, domainModule, dataModule, stripeModule,cacheModule()
)

expect fun cacheModule(): org.koin.core.module.Module
expect fun platformModule(): org.koin.core.module.Module