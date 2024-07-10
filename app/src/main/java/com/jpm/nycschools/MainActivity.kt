package com.jpm.nycschools

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import com.jpm.nycschools.ui.LoadingScreen
import com.jpm.nycschools.ui.NetworkLoadErrorScreen
import com.jpm.nycschools.ui.SchoolDetailScreen
import com.jpm.nycschools.ui.ShowSchoolsScreen
import com.jpm.nycschools.viewmodels.NycSchoolsViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: NycSchoolsViewModel by viewModels()
        viewModel.retrieveRemoteData()

        setContent {
            MaterialTheme {
                val uiState = viewModel.uiState.collectAsState()
                if (uiState.value.remoteLoadError) {
                    NetworkLoadErrorScreen(
                        onClickRetry = {
                            viewModel.retrieveRemoteData()
                        },
                        onClickLoadLocalData = {
                            viewModel.retrieveLocalData()
                        }
                    )
                } else if (uiState.value.loading) {
                    LoadingScreen()
                } else if (uiState.value.chosenSchool != null && uiState.value.chosenSatSchoolInfo != null) {
                    SchoolDetailScreen(
                        school = uiState.value.chosenSchool!!,
                        satScore = uiState.value.chosenSatSchoolInfo!!
                    ) {
                        viewModel.updateChosenSchool(null)
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