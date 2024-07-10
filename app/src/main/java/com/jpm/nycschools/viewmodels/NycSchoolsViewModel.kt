package com.jpm.nycschools.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
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

class NycSchoolsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val schoolsSatScores: HashMap<String, SatScore> = hashMapOf()
    private val schoolList: HashMap<String, School> = hashMapOf()
    private val dataSource: NycSchoolsRemoteDataSource = NycSchoolsRemoteDataSource()
    private var hasRetrievedData = Pair(false, false)
    private val lock = Mutex()

    private suspend fun retrieveSchoolList() {
        val schoolsResponse = dataSource.retrieveNycSchoolsList()
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
        val satScoresResponse = dataSource.retrieveNycSatScoresList()
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

    fun retrieveLocalData() {
        // TODO: load data locally and update UI
    }

    private fun refreshSchoolListUi() {
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

}