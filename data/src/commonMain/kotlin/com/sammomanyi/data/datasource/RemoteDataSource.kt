package com.sammomanyi.data.datasource

import com.sammomanyi.data.model.ApiErrorDto
import com.sammomanyi.data.model.BookingAvailabilityDto
import com.sammomanyi.data.model.BookingDto
import com.sammomanyi.data.model.ListingResponse
import com.sammomanyi.data.model.PaymentIntentDto
import com.sammomanyi.data.model.PaymentIntentInfoRequest
import com.sammomanyi.data.model.SignInResponse
import com.sammomanyi.data.model.TravelListingDto
import com.sammomanyi.data.model.request.BookingInfoRequest
import com.sammomanyi.data.model.request.RegisterRequest
import com.sammomanyi.data.model.request.SignInRequest
import com.sammomanyi.domain.AppError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import kotlinx.io.IOException

class RemoteDataSource(
    private val httpClient: HttpClient,
    private val backendConfig: BackendConfig,
    private val cacheDataSource: CacheDataSource
) {
    private fun getListingByIDEndpoint(id: String) = "${backendConfig.listingsUrl}/$id"
    suspend fun signIn(request: SignInRequest): Result<SignInResponse> {
        return try {
            val response = httpClient.post(urlString = backendConfig.signInUrl) {
                applyPublicApiKey(backendConfig)
                setBody(request)
            }
            Result.success(response.bodyOrError())
        } catch (ex: AppError) {
            Result.failure(ex)
        } catch (ex: IOException) {
            Result.failure(AppError.NetworkError())
        } catch (ex: Exception) {
            Result.failure(AppError.Unknown(ex.message ?: "Unknown error"))
        }
    }

    suspend fun register(request: RegisterRequest): Result<SignInResponse> {
        return try {
            val response = httpClient.post(urlString = backendConfig.registerUrl) {
                applyPublicApiKey(backendConfig)
                setBody(request)
            }
            Result.success(response.bodyOrError())
        } catch (ex: AppError) {
            Result.failure(ex)
        } catch (ex: IOException) {
            Result.failure(AppError.NetworkError())
        } catch (ex: Exception) {
            Result.failure(AppError.Unknown(ex.message ?: "Unknown error"))
        }
    }

    suspend fun getAllListing(): Result<ListingResponse> {
        return try {
            val response = httpClient.get(urlString = backendConfig.listingsUrl) {
                applyAuthorizedHeaders(backendConfig, cacheDataSource)
            }
            Result.success(response.bodyOrError())
        } catch (ex: AppError) {
            Result.failure(ex)
        } catch (ex: IOException) {
            Result.failure(AppError.NetworkError())
        } catch (ex: Exception) {
            Result.failure(AppError.Unknown(ex.message ?: "Unknown error"))
        }
    }

    suspend fun getListingByID(id: String): Result<TravelListingDto> {
        return try {
            val response = httpClient.get(urlString = getListingByIDEndpoint(id)) {
                applyAuthorizedHeaders(backendConfig, cacheDataSource)
            }
            Result.success(response.bodyOrError())
        } catch (ex: AppError) {
            Result.failure(ex)
        } catch (ex: IOException) {
            Result.failure(AppError.NetworkError())
        } catch (ex: Exception) {
            Result.failure(AppError.Unknown(ex.message ?: "Unknown error"))
        }
    }

    suspend fun checkBookingAvailability(request: BookingInfoRequest): Result<BookingAvailabilityDto> {
        return try {
            val response = httpClient.post(urlString = backendConfig.checkAvailabilityUrl) {
                setBody(request)
                applyAuthorizedHeaders(backendConfig, cacheDataSource)
            }
            Result.success(response.bodyOrError())
        } catch (ex: AppError) {
            Result.failure(ex)
        } catch (ex: IOException) {
            Result.failure(AppError.NetworkError())
        } catch (ex: Exception) {
            Result.failure(AppError.Unknown(ex.message ?: "Unknown error"))
        }
    }

    suspend fun createBooking(request: BookingInfoRequest): Result<BookingDto> {
        return try {
            val response = httpClient.post(urlString = backendConfig.bookingsUrl) {
                setBody(request)
                applyAuthorizedHeaders(backendConfig, cacheDataSource)
            }
            Result.success(response.bodyOrError())
        } catch (ex: AppError) {
            Result.failure(ex)
        } catch (ex: IOException) {
            Result.failure(AppError.NetworkError())
        } catch (ex: Exception) {
            Result.failure(AppError.Unknown(ex.message ?: "Unknown error"))
        }
    }

    suspend fun createPaymentIntent(intentInfoRequest: PaymentIntentInfoRequest): Result<PaymentIntentDto> {
        return try {
            val response = httpClient.post(urlString = backendConfig.paymentIntentUrl) {
                setBody(intentInfoRequest)
                applyAuthorizedHeaders(backendConfig, cacheDataSource)
            }
            Result.success(response.bodyOrError())
        } catch (ex: AppError) {
            Result.failure(ex)
        } catch (ex: IOException) {
            Result.failure(AppError.NetworkError())
        } catch (ex: Exception) {
            Result.failure(AppError.Unknown(ex.message ?: "Unknown error"))
        }
    }

    suspend fun getAllBookings(): Result<List<BookingDto>> {
        return try {
            val response = httpClient.get(urlString = backendConfig.bookingsUrl) {
                applyAuthorizedHeaders(backendConfig, cacheDataSource)
            }
            Result.success(response.bodyOrError())
        } catch (ex: AppError) {
            Result.failure(ex)
        } catch (ex: IOException) {
            Result.failure(AppError.NetworkError())
        } catch (ex: Exception) {
            Result.failure(AppError.Unknown(ex.message ?: "Unknown error"))
        }
    }

    private suspend inline fun <reified T> HttpResponse.bodyOrError(): T {
        return if (status.value in 200..299) {
            body()
        } else {

            val errorMessage = try {
                body<ApiErrorDto>().error
            } catch (ex: Exception) {
                null
            }

            throw when (status.value) {
                400 -> AppError.BadRequest(errorMessage ?: "Bad Request")
                401 -> AppError.Unauthorized(errorMessage ?: "Unauthorized")
                403 -> AppError.Forbidden(errorMessage ?: "Forbidden")
                404 -> AppError.NotFound(errorMessage ?: "Not Found")
                409 -> AppError.Conflict(errorMessage ?: "Conflict")
                in 500..599 -> AppError.ServerError(errorMessage ?: "Server Error")
                else -> AppError.Unknown(errorMessage ?: "Unknown Error")
            }
        }
    }

}
