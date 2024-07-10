package com.jpm.nycschools

import com.jpm.nycschools.datasources.NycSchoolsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NycSchoolsRemoteDataSourceIntegrationTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var dataSource: NycSchoolsRemoteDataSource

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        dataSource = NycSchoolsRemoteDataSource()
    }


    @Test
    fun `retrieveNycSchoolsList should return non-empty list of schools`() = runBlocking {
        // Act
        val response = dataSource.retrieveNycSchoolsList()

        // Assert
        assertTrue(response.isSuccessful)
        println("Retrieved data: item count - ${response.data.size}, first item: ${response.data.first()}")
        assertTrue(response.data.isNotEmpty())
    }

    @Test
    fun `retrieveNycSatScoresList should return non-empty list of SAT scores`() = runBlocking {
        // Act
        val response = dataSource.retrieveNycSatScoresList()

        // Assert
        assertTrue(response.isSuccessful)
        println("Retrieved data: item count - ${response.data.size}, first item: ${response.data.first()}")
        assertTrue(response.data.isNotEmpty())
    }
}
