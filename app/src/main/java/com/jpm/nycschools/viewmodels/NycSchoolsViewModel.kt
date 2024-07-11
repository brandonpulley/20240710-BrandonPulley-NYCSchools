package com.jpm.nycschools.viewmodels

import android.content.Context
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.jpm.nycschools.datasources.NycSchoolsLocalDataSource
import com.jpm.nycschools.datasources.NycSchoolsRemoteDataSource
import com.jpm.nycschools.models.SatScore
import com.jpm.nycschools.models.School
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

data class UiState(
    // set this to 'true' when still waiting for network requests to finish
    val loading: Boolean = false,
    // set this to 'true' when we fail retrieving remote data
    val remoteLoadError: Boolean = false,
    // the list of schools to be displayed
    val schoolList: List<School> = listOf(),
    // the school currently being displayed, null if none chosen
    val chosenSchool: School? = null,
    val chosenSatSchoolInfo: SatScore? = null
)

class NycSchoolsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    @VisibleForTesting
    val schoolsSatScores: HashMap<String, SatScore> = hashMapOf()

    @VisibleForTesting
    val schoolList: HashMap<String, School> = hashMapOf()
    private val remoteDataSource: NycSchoolsRemoteDataSource = NycSchoolsRemoteDataSource()

    @VisibleForTesting
    var hasRetrievedData = Pair(false, false)
    private val lock = Mutex()

    private suspend fun retrieveSchoolList() {
        val schoolsResponse = remoteDataSource.retrieveNycSchoolsList()
        if (schoolsResponse.isSuccessful) {
            schoolsResponse.data.forEach { item ->
                val school = item as School
                schoolList[school.dbn] = school
                lock.withLock {
                    hasRetrievedData = Pair(true, hasRetrievedData.second)
                    refreshSchoolListUi()
                }
            }
        } else {
            remoteLoadError()
        }
    }

    private suspend fun retrieveSatScores() {
        val satScoresResponse = remoteDataSource.retrieveNycSatScoresList()
        if (satScoresResponse.isSuccessful) {
            satScoresResponse.data.forEach { item ->
                val satSchool = item as SatScore
                schoolsSatScores[satSchool.dbn] = satSchool
                lock.withLock {
                    hasRetrievedData = Pair(hasRetrievedData.first, true)
                    refreshSchoolListUi()
                }
            }
        } else {
            remoteLoadError()
        }
    }

    private fun remoteLoadError() {
        _uiState.update {
            UiState(remoteLoadError = true)
        }
    }

    fun retrieveRemoteData() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.e("CoroutineException", "exception caught: ${exception.message}")
            remoteLoadError()
        }

        // reset load error value
        _uiState.update { currentState ->
            UiState(
                loading = !hasRetrievedData.first || !hasRetrievedData.second,
                remoteLoadError = false,
                schoolList = currentState.schoolList,
                chosenSchool = currentState.chosenSchool,
                chosenSatSchoolInfo = currentState.chosenSatSchoolInfo
            )
        }

        hasRetrievedData = Pair(false, false)
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            retrieveSchoolList()
        }
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            retrieveSatScores()
        }
    }

    fun retrieveLocalData(context: Context) {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.e("CoroutineException", "exception caught: ${exception.message}")
            remoteLoadError()
        }
        // reset load error value
        _uiState.update { currentState ->
            UiState(
                loading = !hasRetrievedData.first || !hasRetrievedData.second,
                remoteLoadError = false,
                schoolList = currentState.schoolList,
                chosenSchool = currentState.chosenSchool,
                chosenSatSchoolInfo = currentState.chosenSatSchoolInfo
            )
        }

        hasRetrievedData = Pair(false, false)
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            retrieveLocalSchoolsList(context)
        }
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            retrieveLocalSatScoresList(context)
        }
    }

    private suspend fun retrieveLocalSatScoresList(context: Context) {
        val localDataSource = NycSchoolsLocalDataSource(context)
        val satScoresResponse = localDataSource.retrieveNycSatScoresList()
        if (satScoresResponse.isSuccessful) {
            satScoresResponse.data.forEach { item ->
                val satSchool = item as SatScore
                schoolsSatScores[satSchool.dbn] = satSchool
                lock.withLock {
                    hasRetrievedData = Pair(hasRetrievedData.first, true)
                    refreshSchoolListUi()
                }
            }
        } else {
            remoteLoadError()
        }
    }

    private suspend fun retrieveLocalSchoolsList(context: Context) {
        val localDataSource = NycSchoolsLocalDataSource(context)

        val schoolsResponse = localDataSource.retrieveNycSchoolsList()
        if (schoolsResponse.isSuccessful) {
            schoolsResponse.data.forEach { item ->
                val school = item as School
                schoolList[school.dbn] = school
                lock.withLock {
                    hasRetrievedData = Pair(true, hasRetrievedData.second)
                    refreshSchoolListUi()
                }
            }
        } else {
            remoteLoadError()
        }
    }

    @VisibleForTesting
    fun refreshSchoolListUi() {
        _uiState.update { currentState ->
            UiState(
                loading = !hasRetrievedData.first || !hasRetrievedData.second,
                remoteLoadError = currentState.remoteLoadError,
                schoolList = schoolList.values.toList(),
                chosenSchool = currentState.chosenSchool,
                chosenSatSchoolInfo = currentState.chosenSatSchoolInfo
            )
        }
    }

    fun updateChosenSchool(schoolId: String?) {
        _uiState.update { currentState ->
            UiState(
                loading = !hasRetrievedData.first || !hasRetrievedData.second,
                remoteLoadError = currentState.remoteLoadError,
                schoolList = currentState.schoolList,
                chosenSchool = schoolList[schoolId],
                chosenSatSchoolInfo = schoolsSatScores[schoolId]
            )
        }
    }

    fun filterMinimumSatScore(reading: String = "0", writing: String = "0", math: String = "0") {
        try {
            val readingScore = reading.toInt()
            val writingScore = writing.toInt()
            val mathScore = math.toInt()

            _uiState.update { currentState ->
                val filteredSchools = currentState.schoolList.filter {
                    try {
                        (schoolsSatScores[it.dbn]?.satWritingAvgScore?.toInt() ?: 0) > writingScore
                                && (schoolsSatScores[it.dbn]?.satCriticalReadingAvgScore?.toInt()
                            ?: 0) > readingScore
                                && (schoolsSatScores[it.dbn]?.satMathAvgScore?.toInt()
                            ?: 0) > mathScore
                    } catch (e: NumberFormatException) {
                        false
                    }
                }

                UiState(
                    loading = currentState.loading,
                    remoteLoadError = currentState.remoteLoadError,
                    schoolList = filteredSchools.toList(),
                    chosenSchool = currentState.chosenSchool,
                    chosenSatSchoolInfo = currentState.chosenSatSchoolInfo
                )
            }
        } catch (e: NumberFormatException) {
            refreshSchoolListUi()
        }
    }

}