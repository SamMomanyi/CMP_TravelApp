package com.codewithfk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val token: String,
    val user: UserDto
)