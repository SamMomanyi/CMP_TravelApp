package com.sammomanyi.data.repository

import com.sammomanyi.data.datasource.CacheDataSource
import com.sammomanyi.data.datasource.RemoteDataSource
import com.sammomanyi.data.fake.FakeCacheDataSource
import com.sammomanyi.data.jsonResponse
import com.sammomanyi.domain.repository.ListingRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ListingRepositoryImplTest : KoinTest {


    private var mockHandler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData =
        {
            respond(
                content = "",
                status = HttpStatusCode.OK
            )
        }

    private var engine = MockEngine {
        mockHandler(it)
    }

    private val testModule = module {
        single<HttpClient> {
            HttpClient(engine) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
                }
                install(DefaultRequest) {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
            }
        }
        single<CacheDataSource> { FakeCacheDataSource() }
        single { "http://localhost:8080" }
        single { RemoteDataSource(get(), get(), get()) }
        single<ListingRepository> { ListingRepositoryImpl(get()) }
    }


    val repository: ListingRepository by inject()

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

    private fun giveResponse(json: String, status: HttpStatusCode = HttpStatusCode.OK) {
        mockHandler = {
            respond(
                content = json,
                status = status,
                headers = (headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                ))
            )
        }
    }

    @Test
    fun getAllListing_returnSuccess() = runTest {
        giveResponse(jsonResponse, HttpStatusCode.OK)
        val result = repository.getAllListings()
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!.isNotEmpty())
        assertEquals("3fa85f64-5717-4562-b3fc-2c963f66afa6", result.getOrNull()!![0].id)
    }


}