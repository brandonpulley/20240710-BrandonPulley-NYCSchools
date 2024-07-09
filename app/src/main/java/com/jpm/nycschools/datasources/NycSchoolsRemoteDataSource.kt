package com.jpm.nycschools.datasources

import androidx.annotation.VisibleForTesting
import com.jpm.nycschools.api.HostInterface
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class NycSchoolsRemoteDataSource {

    companion object {
        data class ApiResponse(
            val isSuccessful: Boolean,
            val data: List<Any>
        )

        private val HOST_URL = "https://data.cityofnewyork.us"
    }

    @VisibleForTesting
    var retrofit: HostInterface = Retrofit.Builder()
        .baseUrl(HOST_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HostInterface::class.java)

    suspend fun retrieveNycSchoolsList(): ApiResponse {
        val response = retrofit.getNycSchools().awaitResponse()
        return ApiResponse(isSuccessful = response.isSuccessful,
            data = response.body() ?: listOf())
    }

    suspend fun retrieveNycSatScoresList(): ApiResponse {
        val response = retrofit.getNycSatScores().awaitResponse()
        return ApiResponse(isSuccessful = response.isSuccessful,
            data = response.body() ?: listOf())
    }
}