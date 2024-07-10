package com.jpm.nycschools.datasources

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jpm.nycschools.models.SatScore
import com.jpm.nycschools.models.School
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class NycSchoolsLocalDataSource(private val context: Context) {

    companion object {
        data class ApiResponse(
            val isSuccessful: Boolean,
            val data: List<Any>
        )

        private const val SCHOOLS_JSON_FILE = "list_nyc_schools.json"
        private const val SAT_SCORES_JSON_FILE = "list_nyc_sat_scores.json"
    }

    private fun readJsonFile(filename: String): String {
        val inputStream = context.assets.open(filename)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return bufferedReader.use { it.readText() }
    }

    private inline fun <reified T> parseJson(json: String): T {
        val gson = Gson()
        val type = object : TypeToken<T>() {}.type
        return gson.fromJson(json, type)
    }

    suspend fun retrieveNycSchoolsList(): ApiResponse = withContext(Dispatchers.IO) {
        try {
            val json = readJsonFile(SCHOOLS_JSON_FILE)
            val data: List<School> = parseJson(json)
            ApiResponse(isSuccessful = true, data = data)
        } catch (e: Exception) {
            ApiResponse(isSuccessful = false, data = listOf())
        }
    }

    suspend fun retrieveNycSatScoresList(): ApiResponse = withContext(Dispatchers.IO) {
        try {
            val json = readJsonFile(SAT_SCORES_JSON_FILE)
            val data: List<SatScore> = parseJson(json)
            ApiResponse(isSuccessful = true, data = data)
        } catch (e: Exception) {
            ApiResponse(isSuccessful = false, data = listOf())
        }
    }
}