package com.sammomanyi.data.repository

import com.sammomanyi.data.datasource.RemoteDataSource
import com.sammomanyi.data.mappers.PaymentIntentMapper
import com.sammomanyi.data.model.PaymentIntentInfoRequest
import com.sammomanyi.domain.model.PaymentIntent
import com.sammomanyi.domain.repository.PaymentRepository

class PaymentRepositoryImpl(val remoteDataSource: RemoteDataSource) : PaymentRepository {
    override suspend fun createPaymentIntent(
        bookingId: String,
        amount: Double?,
        currency: String
    ): Result<PaymentIntent> {
        val result = remoteDataSource.createPaymentIntent(
            PaymentIntentInfoRequest(
                bookingId = bookingId
            )
        )
        if (result.isSuccess) {
            val response = result.getOrNull()!!
            return Result.success(
                PaymentIntentMapper.toDomain(response)
            )
        } else {
            return Result.failure(result.exceptionOrNull()!!)
        }
    }

}