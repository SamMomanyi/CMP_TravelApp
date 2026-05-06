package com.sammomanyi.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorDto(
    val error: String,
    val details: List<String>? = null
)
