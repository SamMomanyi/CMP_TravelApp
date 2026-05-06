package com.codewithfk.data.di

import com.codewithfk.data.datasource.CacheDataSource
import com.codewithfk.data.datasource.RemoteDataSource
import com.codewithfk.data.repository.BookingRepositoryImpl
import com.codewithfk.data.repository.CacheRepositoryImpl
import com.codewithfk.data.repository.ListingRepositoryImpl
import com.codewithfk.data.repository.PaymentRepositoryImpl
import com.codewithfk.data.repository.UserRepositoryImp
import com.codewithfk.domain.repository.BookingRepository
import com.codewithfk.domain.repository.CacheRepository
import com.codewithfk.domain.repository.ListingRepository
import com.codewithfk.domain.repository.PaymentRepository
import com.codewithfk.domain.repository.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                })
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    single { RemoteDataSource(httpClient = get<HttpClient>(), get(), get()) }

    single<ListingRepository> {
        ListingRepositoryImpl(
            get<RemoteDataSource>()
        )
    }

    single<UserRepository> {
        UserRepositoryImp(
            get<RemoteDataSource>(), get<CacheDataSource>()
        )
    }
    single<CacheRepository> {
        CacheRepositoryImpl(get())
    }

    single<BookingRepository> { BookingRepositoryImpl(get()) }
    single<PaymentRepository> { PaymentRepositoryImpl(get()) }
}