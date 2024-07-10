package com.jpm.nycschools.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpm.nycschools.models.School


@Composable
fun ShowSchoolsScreen(schoolsList: List<School>, onClickSchool: (String) -> Unit) {
    LazyColumn {
        items(schoolsList.size) { index ->
            SchoolView(school = schoolsList[index], onClickSchool)
        }
    }
}

@Composable
fun SchoolView(school: School, onClickSchool: (String) -> Unit) {
    Column(modifier = Modifier
        .background(Color.DarkGray)
        .padding(8.dp)
        .clickable {
            onClickSchool(school.dbn)
        }) {
        Text(
            text = "school name: ${school.schoolName}",
            color = Color.White
        )
    }
}

@Preview
@Composable
fun SchoolViewPreview() {
    val school = School(schoolName = "Examplerary School")
    MaterialTheme {
        SchoolView(school = school) {
            // no op
        }
    }
}