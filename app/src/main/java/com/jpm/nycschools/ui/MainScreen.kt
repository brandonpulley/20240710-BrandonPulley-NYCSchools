package com.jpm.nycschools.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jpm.nycschools.viewmodels.NycSchoolsViewModel

@Composable
fun MainScreen(viewModel: NycSchoolsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        val uiState = viewModel.uiState.collectAsState()

        Text(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .background(Color.Gray),
            text = "NYC Schools",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
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
