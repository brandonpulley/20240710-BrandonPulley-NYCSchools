package com.jpm.nycschools.viewmodels

import androidx.lifecycle.ViewModel
import com.jpm.nycschools.models.School
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UiState(
    // the list of schools to be displayed
    val schoolList: List<School> = listOf(),
    // the school currently being displayed, null if none chosen
    val chosenSchoolId: String? = null,
    // the map of schoolId (key) to satscore (value)
    val schoolsSatScores: Map<String, School> = mapOf()
)

class NycSchoolsViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun retrieveSchoolList() {

    }

    fun updateChosenSchool(schoolId: String) {

    }
}