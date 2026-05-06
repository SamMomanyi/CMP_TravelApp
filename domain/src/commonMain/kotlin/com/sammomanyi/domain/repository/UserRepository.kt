package com.sammomanyi.domain.repository

import com.sammomanyi.domain.model.RegisterModel
import com.sammomanyi.domain.model.UserModel

interface UserRepository {
    suspend fun login(email: String, password: String): Result<UserModel>
    suspend fun register(request: RegisterModel): Result<UserModel>
}