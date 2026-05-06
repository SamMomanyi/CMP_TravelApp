package com.sammomanyi.domain.usecase

import com.sammomanyi.domain.AppError
import com.sammomanyi.domain.fake.FakeListingRepository
import com.sammomanyi.domain.fake.fakeListing
import com.sammomanyi.domain.repository.ListingRepository
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetAllListingUseCaseTest  : KoinTest {

    val fakeRepo = FakeListingRepository()

    val testModule = module {
        single<ListingRepository> { fakeRepo }
        factory { GetAllListingUseCase(get()) }
    }

    val useCase: GetAllListingUseCase  by inject()

    @BeforeTest
    fun setup() {
        startKoin {
           modules( testModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun execute_returnSuccessListing() = runTest {
        // mock response
        fakeRepo.allListing  = Result.success(listOf(fakeListing))

        val result  = useCase.execute()

        assertTrue(result.isSuccess)
        assertEquals(fakeListing.id,result.getOrNull()!![0].id )
    }

    @Test
    fun execute_returnFailure() = runTest {
        // mock response
        val exception = AppError.Unauthorized()
        fakeRepo.allListing = Result.failure(exception)

        val result = useCase.execute()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }













}