package com.jpm.nycschools.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpm.nycschools.models.SatScore
import com.jpm.nycschools.models.School
import com.jpm.nycschools.ui.components.HeaderText
import com.jpm.nycschools.ui.components.Label

@Composable
fun SchoolDetailScreen(school: School, satScore: SatScore) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    ) {
        Label(text = "School name")
        HeaderText(text = school.schoolName)
        Spacer(modifier = Modifier.height(12.dp))
        Label(text = "City")
        HeaderText(text = school.city)
        Spacer(modifier = Modifier.height(12.dp))
        Label(text = "SAT Critical Reading Avg Scores")
        HeaderText(text = satScore.satCriticalReadingAvgScore)
    }
}

@Preview
@Composable
fun SchoolDetailScreenPreview() {
    MaterialTheme {
        SchoolDetailScreen(school =School(
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
        )
    }
}