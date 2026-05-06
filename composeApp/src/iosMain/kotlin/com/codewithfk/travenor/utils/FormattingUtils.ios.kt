package com.codewithfk.travenor.utils

import platform.Foundation.NSDateFormatter
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle

actual object FormattingUtils {
    actual fun formatDate(date: String): String {
        val inputFormat ="yyyy-MM-dd'T'HH:mm:ssZZ'Z'"
        val outputFormat = "dd MMMM yyyy"
        val dataFormatter  = NSDateFormatter()
        dataFormatter.dateFormat = inputFormat
        val parsedDate = dataFormatter.dateFromString(date)
        dataFormatter.dateFormat = outputFormat
        return dataFormatter.stringFromDate(parsedDate!!)
    }

    actual fun formatCurrency(amount: Double, currency: String): String {
        val currencyFormatter = NSNumberFormatter()
        currencyFormatter.numberStyle = NSNumberFormatterCurrencyStyle
        currencyFormatter.currencyCode = currency
        return currencyFormatter.stringFromNumber(NSNumber(amount)) ?: ""
    }
}