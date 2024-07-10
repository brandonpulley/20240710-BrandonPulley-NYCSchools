package com.jpm.nycschools

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import com.jpm.nycschools.ui.ShowSchoolsScreen
import com.jpm.nycschools.viewmodels.NycSchoolsViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: NycSchoolsViewModel by viewModels()
        viewModel.retrieveAllData()
        setContent {
            MaterialTheme {
                val uiState = viewModel.uiState.collectAsState()
                if (uiState.value.chosenSchool != null) {
                    Column {
                        Text(text = "Here's the chosen school name: ${uiState.value.chosenSchool?.schoolName}")
                    }
                } else {
                    ShowSchoolsScreen(uiState.value.schoolList) { schoolId ->
                        viewModel.updateChosenSchool(schoolId = schoolId)
                    }
                }
            }
        }
    }
}