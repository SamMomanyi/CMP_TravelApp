package com.codewithfk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ListingSummaryDto(
    val images: List<String>,
    val location: String,
    val title: String
)