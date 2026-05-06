package com.codewithfk.travenor

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.codewithfk.data.datasource.CacheDataSource
import com.codewithfk.presentation.feature.app.AppViewModel
import com.codewithfk.travenor.navigation.TrevnorNavRoot
import com.codewithfk.travenor.payments.StripePaymentHandler
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(viewModel: AppViewModel = koinViewModel()) {
    MaterialTheme {
        val stripePaymentHandler = getKoin().get<StripePaymentHandler>()
        LaunchedEffect(true) {
            stripePaymentHandler.initialize("pk_test_51T0QTVEiCdIboila6Z68pj2DiuZKwNtj2oY7fySkR3sB6UaG88O04pXpGNY9q5ULKTUMqWrdi2q0VPZIs0x5yu900084DYnX0A")
        }
        val uiSource = viewModel.state.collectAsState()
        if (!uiSource.value.isLoading) {
            TrevnorNavRoot(uiSource.value.authToken)
        } else {
            CircularProgressIndicator()
        }
    }
}

