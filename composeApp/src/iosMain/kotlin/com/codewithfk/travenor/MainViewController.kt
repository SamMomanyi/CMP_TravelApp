package com.codewithfk.travenor

import androidx.compose.ui.window.ComposeUIViewController
import com.codewithfk.travenor.di.appModule
import com.codewithfk.travenor.payments.PaymentSheetBridge
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController(configure = {
    startKoin { modules(appModule) }
}) { App() }


fun registerBridge(
    initialize: (String) -> Unit,
    processPayment: (String, (String) -> Unit) -> Unit
) {
    PaymentSheetBridge.initializeFunction = initialize
    PaymentSheetBridge.processPaymentFunction = processPayment
}