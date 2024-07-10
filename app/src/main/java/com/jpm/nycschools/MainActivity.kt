package com.jpm.nycschools

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import com.jpm.nycschools.ui.ShowSchoolsScreen
import com.jpm.nycschools.viewmodels.NycSchoolsViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: NycSchoolsViewModel by viewModels()
        setContent {
            MaterialTheme {
                val uiState = viewModel.uiState.collectAsState()
                ShowSchoolsScreen(uiState.value.schoolList) { schoolId ->
                    viewModel.updateChosenSchool(schoolId = schoolId)
                }
            }
        }
    }
}