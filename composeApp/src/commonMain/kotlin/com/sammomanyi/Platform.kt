package com.sammomanyi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform