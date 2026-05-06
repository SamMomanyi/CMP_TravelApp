package com.codewithfk.travenor.ui.listing

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.codewithfk.domain.AppError
import com.codewithfk.domain.repository.CacheRepository
import com.codewithfk.domain.repository.ListingRepository
import com.codewithfk.domain.usecase.GetAllListingUseCase
import com.codewithfk.domain.usecase.RemoveAuthTokenUseCase
import com.codewithfk.presentation.feature.listings.TravelListingViewModel
import com.codewithfk.travenor.fake.FakeCacheRepository
import com.codewithfk.travenor.fake.FakeListingRepository
import com.codewithfk.travenor.fake.fakeListing
import com.codewithfk.travenor.navigation.NavRoutes
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class HomeListingScreenTest : KoinTest {

    val fakeListingRepo = FakeListingRepository()

    val testModule = module {
        single<ListingRepository> { fakeListingRepo }
        single<CacheRepository> { FakeCacheRepository() }
        factory { GetAllListingUseCase(get()) }
        factory { RemoveAuthTokenUseCase(get()) }
        factory { TravelListingViewModel(get(), get()) }
    }


    @BeforeTest
    fun setup() {
        startKoin {
            modules(testModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    fun getVM(): TravelListingViewModel = getKoin().get()

    @Composable
    fun ScreenUnderTest(viewModel: TravelListingViewModel) {
        val backStack = rememberNavBackStack(
            configuration = SavedStateConfiguration {
                serializersModule = SerializersModule {
                    polymorphic(NavKey::class) {
                        subclass(NavRoutes.Listing::class, NavRoutes.Listing.serializer())
                    }
                }
            },
            NavRoutes.Listing
        )
        HomeListingScreen(backStack = backStack, viewModel = viewModel)
    }


    @Test
    fun loadListingSuccessfully() = runComposeUiTest {
        fakeListingRepo.allListing = Result.success(listOf(fakeListing))
        val viewModel = getVM()
        setContent {
            ScreenUnderTest(viewModel)
        }
        waitForIdle()
        onNodeWithText("Santorini Greece").assertIsDisplayed()
        onNodeWithTag("listing_card_0").assertIsDisplayed()
        onNodeWithContentDescription("Destination Card for Santorini Greece").assertIsDisplayed()
    }

    @Test
    fun loadListingFailed() = runComposeUiTest {
        fakeListingRepo.allListing = Result.failure(AppError.ServerError())
        val viewModel = getVM()
        setContent {
            ScreenUnderTest(viewModel)
        }
        waitForIdle()
        onNodeWithText("Failed to load listings").assertIsDisplayed()
    }
}