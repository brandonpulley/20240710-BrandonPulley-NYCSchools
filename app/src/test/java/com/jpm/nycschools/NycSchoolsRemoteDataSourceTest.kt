package com.jpm.nycschools

import com.jpm.nycschools.api.HostInterface
import com.jpm.nycschools.datasources.NycSchoolsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class NycSchoolsRemoteDataSourceTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var mockWebServer: MockWebServer
    private lateinit var dataSource: NycSchoolsRemoteDataSource
    private lateinit var retrofit: Retrofit

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockWebServer = MockWebServer()
        mockWebServer.start()

        retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dataSource = NycSchoolsRemoteDataSource().apply {
            retrofit = this@NycSchoolsRemoteDataSourceTest.retrofit.create(HostInterface::class.java)
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

    @Test
    fun `retrieveNycSchoolsList should return successful response when the api is successful`() = runBlocking {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("[]")
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = dataSource.retrieveNycSchoolsList()

        // Assert
        assertEquals(true, response.isSuccessful)
        assertEquals(0, response.data.size)
    }

    @Test
    fun `retrieveNycSchoolsList should return unsuccessful response when the api is unsuccessful`() = runBlocking {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(400)
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = dataSource.retrieveNycSchoolsList()

        // Assert
        assertEquals(false, response.isSuccessful)
        assertEquals(0, response.data.size)
    }

    @Test
    fun `retrieveNycSatScoresList should return successful response when the api is successful`() = runBlocking {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("[]")
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = dataSource.retrieveNycSatScoresList()

        // Assert
        assertEquals(true, response.isSuccessful)
        assertEquals(0, response.data.size)
    }

    @Test
    fun `retrieveNycSatScoresList should return unsuccessful response when the api is unsuccessful`() = runBlocking {
        // Arrange
        val mockResponse = MockResponse()
            .setResponseCode(400)
        mockWebServer.enqueue(mockResponse)

        // Act
        val response = dataSource.retrieveNycSatScoresList()

        // Assert
        assertEquals(false, response.isSuccessful)
        assertEquals(0, response.data.size)
    }
}
