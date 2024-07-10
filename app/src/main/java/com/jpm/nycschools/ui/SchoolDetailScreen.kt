package com.jpm.nycschools.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpm.nycschools.R
import com.jpm.nycschools.models.SatScore
import com.jpm.nycschools.models.School
import com.jpm.nycschools.ui.components.HeaderText
import com.jpm.nycschools.ui.components.Label

@Composable
fun SchoolDetailScreen(school: School, satScore: SatScore, onClickDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(Color.DarkGray)
    ) {
        LabelAndDetailsText(
            label = stringResource(id = R.string.school_name),
            details = school.schoolName
        )
        LabelAndDetailsText(label = stringResource(id = R.string.city), details = school.city)
        LabelAndDetailsText(
            label = stringResource(id = R.string.neighborhood),
            details = school.neighborhood
        )
        LabelAndDetailsText(
            label = stringResource(id = R.string.final_grades),
            details = school.finalGrades
        )
        LabelAndDetailsText(
            label = stringResource(id = R.string.num_sat_takers),
            details = satScore.numOfSatTestTakers
        )
        LabelAndDetailsText(
            label = stringResource(id = R.string.sat_reading_score),
            details = satScore.satCriticalReadingAvgScore
        )
        LabelAndDetailsText(
            label = stringResource(id = R.string.sat_writing_score),
            details = satScore.satWritingAvgScore
        )
        LabelAndDetailsText(
            label = stringResource(id = R.string.sat_math_score),
            details = satScore.satMathAvgScore
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickDismiss
        ) {
            Text(text = stringResource(id = R.string.dismiss))
        }
    }
}

@Composable
fun LabelAndDetailsText(label: String, details: String) {
    Label(text = label)
    HeaderText(text = details)
    Spacer(modifier = Modifier.height(12.dp))
}

@Preview
@Composable
fun SchoolDetailScreenPreview() {
    MaterialTheme {
        SchoolDetailScreen(
            school = School(
                schoolName = "A Neat School",
                city = "New Jork"
            ), satScore = SatScore(
                schoolName = "A neat school",
                dbn = "",
                numOfSatTestTakers = "22",
                satWritingAvgScore = "600",
                satMathAvgScore = "500",
                satCriticalReadingAvgScore = "550"
            )
        ) {
            // no op
        }
    }
}