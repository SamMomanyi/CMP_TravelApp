package com.codewithfk.domain.repository

import com.codewithfk.domain.model.RegisterModel
import com.codewithfk.domain.model.UserModel

interface UserRepository {
    suspend fun login(email: String, password: String): Result<UserModel>
    suspend fun register(request: RegisterModel): Result<UserModel>
}