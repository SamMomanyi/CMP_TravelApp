package com.codewithfk.travenor.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.codewithfk.travenor.ui.bookings.BookingListScreen
import com.codewithfk.travenor.ui.checkout.CheckoutScreen
import com.codewithfk.travenor.ui.details.TravelItemDetailsScreen
import com.codewithfk.travenor.ui.listing.HomeListingScreen
import com.codewithfk.travenor.ui.signin.LoginScreen
import com.codewithfk.travenor.ui.signup.SignUpScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun TrevnorNavRoot(userToken: String?) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(NavRoutes.Login::class, NavRoutes.Login.serializer())
                    subclass(NavRoutes.SignUp::class, NavRoutes.SignUp.serializer())
                    subclass(NavRoutes.Listing::class, NavRoutes.Listing.serializer())
                    subclass(NavRoutes.Checkout::class, NavRoutes.Checkout.serializer())
                    subclass(NavRoutes.BookingList::class, NavRoutes.BookingList.serializer())
                }
            }
        },
        if(!userToken.isNullOrEmpty()) NavRoutes.Listing else NavRoutes.Login
    )

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->

            when (key) {
                is NavRoutes.Login -> NavEntry(key) {
                    LoginScreen(backStack = backStack)
                }

                is NavRoutes.SignUp -> NavEntry(key) {
                    SignUpScreen(backStack = backStack)
                }

                is NavRoutes.Listing -> NavEntry(key) {
                    HomeListingScreen(backStack = backStack)
                }

                is NavRoutes.ListingDetails -> NavEntry(key) {
                    TravelItemDetailsScreen(backStack = backStack, itemId = key.id)
                }
                is NavRoutes.Checkout -> NavEntry(key) {
                    CheckoutScreen(backStack = backStack, itemId = key.id)
                }
                is NavRoutes.BookingList -> NavEntry(key) {
                    BookingListScreen(backStack = backStack)
                }
                else -> error("Unknown NavRoute: $key")
            }
        }
    )
}