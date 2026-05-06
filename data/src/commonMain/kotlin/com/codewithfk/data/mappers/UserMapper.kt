package com.codewithfk.data.mappers

import com.codewithfk.data.model.UserDto
import com.codewithfk.domain.model.UserModel

object UserMapper {
    fun toDomain(dto: UserDto): UserModel {
        return UserModel(
            id = dto.id,
            firstName = dto.firstName,
            lastName = dto.lastName,
            email = dto.email,
        )
    }

    fun toDomain(dtos: List<UserDto>): List<UserModel> {
        return dtos.map { toDomain(it) }
    }
}