package com.jpm.nycschools.datasources

import com.jpm.nycschools.api.HostInterface
import com.jpm.nycschools.models.School
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class NycSchoolsRemoteDataSource {

    private val retrofit: HostInterface = Retrofit.Builder()
        .baseUrl("https://data.cityofnewyork.us")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(HostInterface::class.java)

    suspend fun retrieveNycSchoolsList(): List<School> {
        val response = retrofit.getNycSchools().awaitResponse()
        // TODO: check for errors and react appropriately,
        //  this assumes the endpoint always works correctly
        return response.body() ?: listOf()
    }
}