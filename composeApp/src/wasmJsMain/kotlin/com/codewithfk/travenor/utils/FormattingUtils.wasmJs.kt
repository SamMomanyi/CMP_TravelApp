package com.codewithfk.travenor.utils

actual object FormattingUtils {
    @OptIn(ExperimentalWasmJsInterop::class)
    actual fun formatDate(date: String): String {
        return jsFormatDate(date)
    }

    actual fun formatCurrency(amount: Double, currency: String): String {
        return jsFormatCurrency(amount, currency)
    }
}

@OptIn(ExperimentalWasmJsInterop::class)
@JsFun("""
   (date) => {
        const inputFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        const outputFormat = "dd MMMM yyyy";
        const parsedDate = new Date(date);
        const options = { day: '2-digit', month: 'long', year: 'numeric' };
        return parsedDate.toLocaleDateString(undefined, options);
    }
""")
external fun jsFormatDate(date: String): String

@JsFun("""
   (amount, currency) => {
        const formatter = new Intl.NumberFormat(undefined, {
            style: 'currency',
            currency: currency
        });
        return formatter.format(amount);
    }
""")
external fun jsFormatCurrency(amount: Double, currency: String): String