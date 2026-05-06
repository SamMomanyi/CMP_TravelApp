package com.sammomanyi.ui.checkout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil3.compose.AsyncImage
import com.sammomanyi.domain.model.BookingAvailability
import com.sammomanyi.domain.model.TravelListing
import com.sammomanyi.domain.model.TripDate
import com.sammomanyi.presentation.feature.checkout.CheckoutViewModel
import com.sammomanyi.navigation.NavRoutes
import com.sammomanyi.payments.PaymentResult
import com.sammomanyi.payments.StripePaymentHandler
import com.sammomanyi.utils.FormattingUtils
import com.sammomanyi.widgets.TravenorCircleImageButton
import com.sammomanyi.widgets.TravenorSpacer
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    backStack: NavBackStack<NavKey>,
    itemId: String,
    viewModel: CheckoutViewModel = koinViewModel { parametersOf(itemId) }
) {

    Scaffold {

        val shouldShowErrorDialog = remember { mutableStateOf(false) }
        val paymentHandler = remember { StripePaymentHandler() }
        val uiState = viewModel.uiState.collectAsState()
        LaunchedEffect(uiState.value.paymentIntent) {
            uiState.value.paymentIntent?.let {
                val resultStatus = paymentHandler.processPayment(it.clientSecret)
                when (resultStatus) {
                    is PaymentResult.Success -> {
                        backStack.removeAll(
                            listOf(
                                NavRoutes.Checkout(itemId),
                                NavRoutes.ListingDetails(itemId)
                            )
                        )
                        backStack.add(NavRoutes.BookingList)
                    }

                    is PaymentResult.Failure -> {
                        shouldShowErrorDialog.value = true
                    }

                    is PaymentResult.Cancelled -> {

                    }
                }
            }
        }
        if (shouldShowErrorDialog.value) {
            AlertDialog(
                onDismissRequest = { shouldShowErrorDialog.value = false },
                title = { Text("Payment Failed") },
                text = { Text("There was an issue processing your payment. Please try again.") },
                confirmButton = {
                    Button(onClick = { shouldShowErrorDialog.value = false }) {
                        Text("OK")
                    }
                }
            )
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            if (uiState.value.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text("Loading...")
                }
            }

            if (uiState.value.errorMessage != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Error: ${uiState.value.errorMessage}")
                    Button(onClick = { viewModel.getListingDetails() }) {
                        Text("Retry")
                    }
                }
            }

            uiState.value.listing?.let { listing ->
                TravenorSpacer(16.dp)
                CheckoutContent(
                    listing = listing,
                    onDateItemSelected = { tripDateId ->
                        viewModel.selectTripDate(tripDateId)
                    },
                    selectedTripDateId = uiState.value.selectedTripDateId ?: "",
                    noOfGuest = uiState.value.numberOfGuests,
                    isCheckingAvailability = uiState.value.isCheckingAvailability,
                    availability = uiState.value.bookingAvailability,
                    onGuestAdded = { viewModel.addGuest() },
                    onGuestRemoved = { viewModel.removeGuest() },
                    proceedToPayment = { viewModel.createBooking() }
                )
            }
        }
    }
}

@Composable
fun CheckoutContent(
    listing: TravelListing,
    onDateItemSelected: (String) -> Unit,
    selectedTripDateId: String,
    noOfGuest: Int,
    isCheckingAvailability: Boolean = false,
    availability: BookingAvailability? = null,
    onGuestAdded: () -> Unit,
    onGuestRemoved: () -> Unit,
    proceedToPayment: () -> Unit
) {
    Column {
        CheckoutHeader(listing = listing)
        listing.tripDates?.let {
            TripDates(it, selectedTripDateId, onItemSelected = { onDateItemSelected.invoke(it) })
        }
        AnimatedVisibility(selectedTripDateId.isNotBlank()) {
            NoOfGuestsSelector(
                noOfGuest = noOfGuest,
                onGuestAdded = onGuestAdded,
                onGuestRemoved = onGuestRemoved
            )
        }

        AnimatedVisibility(isCheckingAvailability) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                TravenorSpacer(8.dp)
                Text(
                    text = "Checking Availability...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        AnimatedVisibility(availability != null) {
            availability?.let {
                BookingAvailabilitySection(bookingAvailability = it)
            }
        }
        availability?.let {
            Button(onClick = proceedToPayment, modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                Text("Proceed to Payment", modifier = Modifier.padding(8.dp))
            }
        }

    }
}

@Composable
fun BookingAvailabilitySection(bookingAvailability: BookingAvailability) {
    Card(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Booking Details",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.size(8.dp))
            bookingAvailability.priceCalculation?.let {
                PriceRow(
                    label = "Sub Total:",
                    value = FormattingUtils.formatCurrency(it.subtotal, it.currency)
                )
                PriceRow(
                    label = "Taxes:",
                    value = FormattingUtils.formatCurrency(it.taxes, it.currency)
                )
                PriceRow(
                    label = "Service Fee:",
                    value = FormattingUtils.formatCurrency(it.serviceFee, it.currency)
                )
                PriceRow(
                    label = "Total:",
                    value = FormattingUtils.formatCurrency(it.total, it.currency)
                )
            }
        }
    }
}

@Composable
fun PriceRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun TripDates(
    tripDates: List<TripDate>,
    isSelected: String,
    onItemSelected: (String) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
            .padding(16.dp).testTag("trip_dates_card"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Select Trip Dates",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.size(8.dp))
            tripDates.forEach { tripDate ->
                Row(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                        .clickable { onItemSelected.invoke(tripDate.id) }
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = isSelected == tripDate.id, onClick = null)
                    TravenorSpacer(8.dp)
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "CheckIN: ${FormattingUtils.formatDate(tripDate.startDate)}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = "CheckOUT: ${FormattingUtils.formatDate(tripDate.endDate)}",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                    Text(
                        "${tripDate.availableSpots} Spots Left",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NoOfGuestsSelector(
    noOfGuest: Int,
    onGuestAdded: () -> Unit,
    onGuestRemoved: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Text(
                text = "Number of Guests",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onGuestRemoved.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Remove Guest",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(48.dp)
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(6.dp)
                    )
                }
                Text(
                    text = noOfGuest.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(16.dp)
                )
                IconButton(onClick = { onGuestAdded.invoke() }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Guest",
                        tint = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier.size(48.dp)
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(6.dp)

                    )
                }
            }
        }

    }

}

@Composable
fun CheckoutHeader(listing: TravelListing) {
    Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
        listing.images?.firstOrNull()?.let {
            AsyncImage(
                model = it,
                contentDescription = "Listing Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            CheckOutTopBar(onBackClick = {})

            Column(modifier = Modifier.align(Alignment.BottomStart)) {
                Text(
                    text = listing.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = listing.location,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 12.dp)
                )
            }
        }
    }
}


@Composable
fun CheckOutTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TravenorCircleImageButton(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back Arrow",
            modifier = Modifier,
            onClick = {})
        Text(
            "Checkout",
            modifier = Modifier.weight(1f).padding(16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.size(48.dp))
    }
}










