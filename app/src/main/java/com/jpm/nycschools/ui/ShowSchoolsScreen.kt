package com.jpm.nycschools.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpm.nycschools.R
import com.jpm.nycschools.models.School


@Composable
fun ShowSchoolsScreen(schoolsList: List<School>, onClickSchool: (String) -> Unit) {
    LazyColumn {
        items(schoolsList.size) { index ->
            SchoolView(school = schoolsList[index], onClickSchool)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SchoolView(school: School, onClickSchool: (String) -> Unit) {
    Column(modifier = Modifier
        .background(Color.DarkGray)
        .padding(8.dp)
        .fillMaxWidth()
        .clickable {
            onClickSchool(school.dbn)
        }) {
        LabelAndDetailsText(
            label = stringResource(id = R.string.school_name),
            details = school.schoolName
        )
        LabelAndDetailsText(
            label = stringResource(id = R.string.neighborhood),
            details = school.neighborhood
        )
    }
}

@Preview
@Composable
fun ShowSchoolsScreenPreview() {
    val schools = listOf(
        School(schoolName = "Examplerary School", neighborhood = "Brooklyn"),
        School(schoolName = "Another School", neighborhood = "Hunts Point"),
        School(schoolName = "a Third School", neighborhood = "Old New York"),
    )
    MaterialTheme {
        ShowSchoolsScreen(schoolsList = schools) {
            // no op
        }
    }
}