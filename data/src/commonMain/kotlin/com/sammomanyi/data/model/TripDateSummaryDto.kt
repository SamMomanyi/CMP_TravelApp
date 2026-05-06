package com.sammomanyi.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TripDateSummaryDto(
    val endDate: String,
    val startDate: String
)