package com.jpm.nycschools.viewmodels

import androidx.lifecycle.ViewModel
import com.jpm.nycschools.models.SatScore
import com.jpm.nycschools.models.School
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

    private val schoolsSatScores: Map<String, SatScore> = mapOf()
    private val schoolList: Map<String, School> = mapOf()

    fun retrieveSchoolList() {

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