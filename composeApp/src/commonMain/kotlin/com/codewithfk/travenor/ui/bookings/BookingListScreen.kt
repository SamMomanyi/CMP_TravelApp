package com.codewithfk.travenor.ui.bookings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import coil3.compose.AsyncImage
import com.codewithfk.domain.model.Booking
import com.codewithfk.presentation.feature.bookings.BookingListViewModel
import com.codewithfk.travenor.utils.FormattingUtils
import com.codewithfk.travenor.widgets.TravenorSpacer
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun BookingListScreen(
    backStack: NavBackStack<NavKey>,
    viewModel: BookingListViewModel = koinViewModel()
) {

    Scaffold {
        val uiState = viewModel.uiState.collectAsState()

        if (uiState.value.isLoading) {
            Column(modifier = Modifier.padding(it).fillMaxSize()) {
                CircularProgressIndicator()
                Text(text = "Loading bookings...")
            }
        } else if (uiState.value.errorMessage != null) {
            Column(modifier = Modifier.padding(it).fillMaxSize()) {
                Text(text = "Error: ${uiState.value.errorMessage}")
            }
        } else {
            if (uiState.value.bookings.isEmpty()) {
                Column(modifier = Modifier.padding(it).fillMaxSize()) {
                    Text(text = "No bookings found.")
                }
            } else
                LazyColumn(modifier = Modifier.padding(it).fillMaxSize().padding(16.dp)) {
                    items(uiState.value.bookings) { booking ->
                        BookingListItem(booking = booking)
                    }
                }
        }
    }

}

@Composable
fun BookingListItem(booking: Booking) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            booking.listing?.images?.firstOrNull()?.let { imageUrl ->

                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Listing Image",
                    modifier = Modifier.padding(end = 16.dp).size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }
            TravenorSpacer(16.dp)
            Column(modifier = Modifier) {
                Text(text = "${booking.listing?.title ?: "Unknown"}")
                Text(
                    text = "${
                        FormattingUtils.formatDate(booking.tripDate!!.startDate!!)
                    } - ${FormattingUtils.formatDate(booking.tripDate!!.endDate!!)}"
                )
            }

        }

    }
}

















