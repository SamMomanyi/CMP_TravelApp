package com.codewithfk.presentation.feature.listing

import com.codewithfk.domain.AppError
import com.codewithfk.domain.repository.CacheRepository
import com.codewithfk.domain.repository.ListingRepository
import com.codewithfk.domain.usecase.GetAllListingUseCase
import com.codewithfk.domain.usecase.RemoveAuthTokenUseCase
import com.codewithfk.presentation.fake.FakeCacheRepository
import com.codewithfk.presentation.fake.FakeListingRepository
import com.codewithfk.presentation.fake.fakeListing
import com.codewithfk.presentation.feature.listings.TravelListingNavigation
import com.codewithfk.presentation.feature.listings.TravelListingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class TravelListingViewModelTest : KoinTest {


    val testDispatcher = StandardTestDispatcher()
    val fakeCacheRepository = FakeCacheRepository()
    val fakeListingRepository = FakeListingRepository()

    private val testModule = module {
        single<CacheRepository> { fakeCacheRepository }
        single<ListingRepository> { fakeListingRepository }
        factory { GetAllListingUseCase(get()) }
        factory { RemoveAuthTokenUseCase(get()) }
        factory { TravelListingViewModel(get(), get()) }
    }


    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        startKoin {
            modules(testModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    fun getVM(): TravelListingViewModel = getKoin().get()

    @Test
    fun loadTravelListings_returnSuccess() = runTest(testDispatcher) {
        fakeListingRepository.allListing = Result.success(listOf(fakeListing))
        val vm = getVM()
        advanceUntilIdle()
        assertTrue(vm.state.value.listings.isNotEmpty())
    }

    @Test
    fun loadTravelListings_returnFailure() = runTest(testDispatcher) {
        fakeListingRepository.allListing = Result.failure(AppError.Unknown())
        val vm = getVM()
        advanceUntilIdle()
        assertTrue(vm.state.value.errorMessage is AppError.Unknown)
        assertTrue(vm.state.value.listings.isEmpty())
    }


    @Test
    fun loadTravelListings_UnAuthorized_Take_User_To_Login() = runTest(testDispatcher) {
        fakeListingRepository.allListing = Result.failure(AppError.Unauthorized())

        val vm = getVM() //loaded listing in init block and failed with unauthorized error
        advanceUntilIdle()

        val navigationEvents = mutableListOf<TravelListingNavigation>()
        val job = launch {
            vm.navigationState.collect {
                navigationEvents.add(it)
            }
        }

        assertTrue(vm.state.value.errorMessage is AppError.Unauthorized)
        vm.handleError() // retry was attempted and should have taken user to login screen
        advanceUntilIdle()
        assertTrue(navigationEvents.contains(TravelListingNavigation.GoToLogin))
        job.cancel()

    }


}