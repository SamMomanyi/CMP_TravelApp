package com.codewithfk.data.repository

import com.codewithfk.data.datasource.RemoteDataSource
import com.codewithfk.data.mappers.BookingMapper
import com.codewithfk.data.mappers.PriceCalculationMapper
import com.codewithfk.data.model.request.BookingInfoRequest
import com.codewithfk.domain.model.Booking
import com.codewithfk.domain.model.BookingAvailability
import com.codewithfk.domain.repository.BookingRepository

class BookingRepositoryImpl(private val remoteDataSource: RemoteDataSource) : BookingRepository {
    override suspend fun checkAvailability(
        listingId: String,
        tripDateID: String,
        numberOfGuests: Int
    ): Result<BookingAvailability> {

        val result = remoteDataSource.checkBookingAvailability(
            BookingInfoRequest(
                listingId = listingId,
                tripDateId = tripDateID,
                numberOfGuests = numberOfGuests
            )
        )
        if (result.isSuccess) {
            val response = result.getOrNull()!!
            return Result.success(
                BookingAvailability(
                    available = response.available,
                    priceCalculation = PriceCalculationMapper.toDomain(response.priceCalculation),
                    reason = response.reason
                )
            )
        } else {
            return Result.failure(result.exceptionOrNull()!!)
        }

    }

    override suspend fun createBooking(
        listingId: String,
        tripDateID: String,
        numberOfGuests: Int,
        specialRequests: String?
    ): Result<Booking> {

        val result = remoteDataSource.createBooking(
            BookingInfoRequest(
                listingId = listingId,
                tripDateId = tripDateID,
                numberOfGuests = numberOfGuests,
                specialRequests = specialRequests
            ))

        if (result.isSuccess) {
            val response = result.getOrNull()!!
            return Result.success(
                BookingMapper.toDomain(response)
            )
        } else {
            return Result.failure(result.exceptionOrNull()!!)
        }
    }

    override suspend fun getBookings(): Result<List<Booking>> {
        val result = remoteDataSource.getAllBookings()
        if (result.isSuccess) {
            val response = result.getOrNull()!!
            return Result.success(
                BookingMapper.toDomain(response)
            )
        } else {
            return Result.failure(result.exceptionOrNull()!!)
        }
    }
}