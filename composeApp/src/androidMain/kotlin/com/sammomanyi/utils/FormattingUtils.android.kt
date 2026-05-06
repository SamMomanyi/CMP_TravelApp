package com.sammomanyi.utils

import java.text.SimpleDateFormat
import java.util.Locale

actual object FormattingUtils {
    actual fun formatDate(date: String): String {
        //2025-12-20T00:00:00Z
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val parsedDate = inputFormat.parse(date)
        return outputFormat.format(parsedDate!!)
    }

    actual fun formatCurrency(amount: Double, currency: String): String {
        val currencyFormatter = java.text.NumberFormat.getCurrencyInstance()
        currencyFormatter.currency = java.util.Currency.getInstance(currency)
        return currencyFormatter.format(amount)
    }
}