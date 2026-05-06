package com.codewithfk.data.repository

import com.codewithfk.data.datasource.RemoteDataSource
import com.codewithfk.data.mappers.PaymentIntentMapper
import com.codewithfk.data.model.PaymentIntentInfoRequest
import com.codewithfk.domain.model.PaymentIntent
import com.codewithfk.domain.repository.PaymentRepository

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