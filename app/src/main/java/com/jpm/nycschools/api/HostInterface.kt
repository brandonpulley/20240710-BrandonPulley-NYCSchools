package com.jpm.nycschools.api

import com.jpm.nycschools.models.SatScore
import com.jpm.nycschools.models.School
import retrofit2.Call
import retrofit2.http.GET

interface HostInterface {

    @GET("/resource/s3k6-pzi2.json")
    fun getNycSchools(): Call<List<School>>

    @GET("/resource/f9bf-2cp4.json")
    fun getNycSatScores(): Call<List<SatScore>>
}