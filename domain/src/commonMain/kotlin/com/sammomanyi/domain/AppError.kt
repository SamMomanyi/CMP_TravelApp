package com.sammomanyi.domain

sealed class AppError : Exception() {
    data class Unauthorized(override val message: String = "Session expired. Please log in again.") : AppError()
    data class Forbidden(override val message: String = "You don't have permission to do this.") : AppError()
    data class NotFound(override val message: String = "The requested resource was not found.") : AppError()
    data class Conflict(override val message: String = "This already exists.") : AppError()
    data class BadRequest(override val message: String) : AppError()
    data class ServerError(override val message: String = "Server error. Please try again.") : AppError()
    data class NetworkError(override val message: String = "No internet connection.") : AppError()
    data class Unknown(override val message: String = "An unexpected error occurred.") : AppError()

}

fun Throwable.toReadableMessage(): String {
    return when (this) {
        is AppError -> this.message ?: "An error occurred."
        else -> "An unexpected error occurred."
    }
}