package com.sammomanyi.data.repository

import com.sammomanyi.data.datasource.CacheDataSource
import com.sammomanyi.data.datasource.RemoteDataSource
import com.sammomanyi.data.mappers.RegisterRequestMapper
import com.sammomanyi.data.mappers.UserMapper
import com.sammomanyi.data.model.request.SignInRequest
import com.sammomanyi.domain.model.RegisterModel
import com.sammomanyi.domain.model.UserModel
import com.sammomanyi.domain.repository.UserRepository

class UserRepositoryImp(
    val dataSource: RemoteDataSource,
    private val cacheDataSource: CacheDataSource
) : UserRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Result<UserModel> {

        return try {
            val response = dataSource.signIn(SignInRequest(email, password))
            if (response.isSuccess) {
                val response = response.getOrNull()!!
                val userModel = UserMapper.toDomain(response.user)
                cacheDataSource.saveAuthToken(response.token)
                Result.success(userModel)
            } else {
                Result.failure(response.exceptionOrNull()!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(request: RegisterModel): Result<UserModel> {
        return try {
            val response = dataSource.register(RegisterRequestMapper.toDto(request))
            if (response.isSuccess) {
                val response = response.getOrNull()!!
                val userModel = UserMapper.toDomain(response.user)
                cacheDataSource.saveAuthToken(response.token)
                Result.success(userModel)
            } else {
                Result.failure(response.exceptionOrNull()!!)
            }

        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}