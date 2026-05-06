package com.sammomanyi.data.repository

import com.sammomanyi.data.datasource.RemoteDataSource
import com.sammomanyi.data.mappers.TravelListingMapper
import com.sammomanyi.domain.model.TravelListing
import com.sammomanyi.domain.repository.ListingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class ListingRepositoryImpl(val dataSource: RemoteDataSource) : ListingRepository {

    override suspend fun getAllListings(): Result<List<TravelListing>> {
        val dtos = dataSource.getAllListing()
        if (dtos.isSuccess) {
            val listings = dtos.getOrNull()!!.listings
            val models = TravelListingMapper.toDomain(listings)
            return Result.success(models)
        } else {
            return Result.failure(dtos.exceptionOrNull()!!)
        }
    }

    override suspend fun getListingById(id: String): Result<TravelListing?> {
        val dtoResult = dataSource.getListingByID(id)
        if (dtoResult.isSuccess) {
            val dto = dtoResult.getOrNull()
            val model = dto?.let { TravelListingMapper.toDomain(it) }
            return Result.success(model)
        }else {
            return Result.failure(dtoResult.exceptionOrNull()!!)
        }
    }
}