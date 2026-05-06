package com.codewithfk.travenor.utils

expect object FormattingUtils {
    fun formatDate(date: String): String
    fun formatCurrency(amount: Double, currency: String): String
}