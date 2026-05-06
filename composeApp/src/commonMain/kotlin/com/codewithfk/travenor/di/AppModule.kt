package com.codewithfk.travenor.di

import com.codewithfk.data.di.dataModule
import com.codewithfk.domain.di.domainModule
import com.codewithfk.presentation.di.presentationModule
import com.codewithfk.travenor.payments.StripePaymentHandler
import org.koin.dsl.module

val stripeModule = module {
    single { StripePaymentHandler() }
}
val appModule = listOf(
    platformModule(), presentationModule, domainModule, dataModule, stripeModule,cacheModule()
)

expect fun cacheModule(): org.koin.core.module.Module
expect fun platformModule(): org.koin.core.module.Module