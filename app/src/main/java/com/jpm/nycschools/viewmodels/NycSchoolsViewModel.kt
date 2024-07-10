package com.jpm.nycschools.viewmodels

import androidx.lifecycle.ViewModel
import com.jpm.nycschools.datasources.NycSchoolsRemoteDataSource
import com.jpm.nycschools.models.SatScore
import com.jpm.nycschools.models.School
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
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

    private suspend fun retrieveSchoolList() {
        val schoolsResponse = dataSource.retrieveNycSchoolsList()
        if (schoolsResponse.isSuccessful) {
            schoolsResponse.data.forEach { item ->
                val school = item as School
                schoolList[school.dbn] = school
                refreshSchoolListUi()
            }
        } else {
            // TODO: update UI for failure to get school response
        }
    }

    private suspend fun retrieveSatScores() {
        val satScoresResponse = dataSource.retrieveNycSatScoresList()
        if (satScoresResponse.isSuccessful) {
            satScoresResponse.data.forEach { item ->
                val satSchool = item as SatScore
                schoolsSatScores[satSchool.dbn] = satSchool
            }
        } else {
            // TODO: update UI for failure to get sat response
        }
    }

    fun retrieveAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            retrieveSchoolList()
        }
        CoroutineScope(Dispatchers.IO).launch {
            retrieveSatScores()
        }
    }

    fun refreshSchoolListUi() {
        _uiState.update { currentState ->
            UiState(
                schoolList = schoolList.values.toList(),
                chosenSchool = currentState.chosenSchool,
                chosenSatSchoolInfo = currentState.chosenSatSchoolInfo
            )
        }
    }

    fun updateChosenSchool(schoolId: String?) {
        _uiState.update { currentState ->
            UiState(
                schoolList = currentState.schoolList,
                chosenSchool = schoolList[schoolId],
                chosenSatSchoolInfo = schoolsSatScores[schoolId]
            )
        }
    }

}