package com.codewithfk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val email: String,
    val firstName: String,
    val id: String,
    val isActive: Boolean,
    val lastName: String,
    val phone: String,
    val role: String
)