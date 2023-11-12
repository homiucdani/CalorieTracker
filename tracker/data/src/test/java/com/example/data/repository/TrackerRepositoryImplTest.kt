package com.example.data.repository


import com.example.data.remote.OpenFoodApi
import com.example.data.remote.malformedFoodResponse
import com.example.data.remote.validFoodResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class TrackerRepositoryImplTest {

    private lateinit var repositoryImpl: TrackerRepositoryImpl

    // construct a local web server which returns a fake response to test our code
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okkHttpClient: OkHttpClient
    private lateinit var api: OpenFoodApi

    @Before
    fun setUp() {
        //server
        mockWebServer = MockWebServer()
        // config for server
        okkHttpClient = OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .build()

        //request
        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okkHttpClient)
            .baseUrl(mockWebServer.url("/")) // initial path
            .build()
            .create(OpenFoodApi::class.java)

        repositoryImpl = TrackerRepositoryImpl(
            dao = mockk(relaxed = true),
            api = api
        )
    }

    // after the tests finish, close what needs to be closed
    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    // run blocking helps us to execute a suspend fun inside the test, (a coroutine function)
    @Test
    fun `Search food, valid response, returns success`() = runBlocking {
        // with what values the server should response to our call
        mockWebServer.enqueue(
            response = MockResponse()
                .setResponseCode(200)
                .setBody(validFoodResponse)
        )

        val result = repositoryImpl.searchFood("apple", 1, 40)

        assertThat(result.isSuccess).isTrue()
    }

    @Test
    fun `Search food, invalid response, returns failure`() = runBlocking {
        // with what values the server should response to our call
        mockWebServer.enqueue(
            response = MockResponse()
                .setResponseCode(403) // forbidden
                .setBody(validFoodResponse)
        )

        val result = repositoryImpl.searchFood("apple", 1, 40)

        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `Search food, malformed response, returns malformed result`() = runBlocking {
        // with what values the server should response to our call
        mockWebServer.enqueue(
            response = MockResponse()
                .setBody(malformedFoodResponse)
        )

        val result = repositoryImpl.searchFood("apple", 1, 40)

        assertThat(result.isFailure).isTrue()
    }
}