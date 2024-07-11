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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jpm.nycschools.R
import com.jpm.nycschools.models.School


@Composable
fun ShowSchoolsScreen(
    schoolsList: List<School>,
    onClickSchool: (String) -> Unit,
    onSatScoresFiltered: (String, String, String) -> Unit
) {
    var minReadingScore by remember { mutableStateOf("") }
    var minWritingScore by remember { mutableStateOf("") }
    var minMathScore by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        fun updateFilter() {
            onSatScoresFiltered(minReadingScore, minWritingScore, minMathScore)
        }

        LazyColumn {
            items(schoolsList.size + 3) { index ->
                // first three items in this scrollable list are the filters
                when (index) {
                    0 -> {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = minWritingScore,
                            onValueChange = { newText ->
                                minWritingScore = newText
                                updateFilter()
                            },
                            label = { Text("Min Avg Writing Score") },
                            placeholder = {
                                Text(stringResource(id = R.string.sat_filter_hint))
                            }
                        )
                    }
                    1 -> {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = minReadingScore,
                            onValueChange = { newText ->
                                minReadingScore = newText
                                updateFilter()
                            },
                            label = { Text("Min Avg Reading Score") },
                            placeholder = {
                                Text(stringResource(id = R.string.sat_filter_hint))
                            }
                        )
                    }
                    2 -> {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = minMathScore,
                            onValueChange = { newText ->
                                minMathScore = newText
                                updateFilter()
                            },
                            label = { Text("Min Avg Math Score") },
                            placeholder = {
                                Text(stringResource(id = R.string.sat_filter_hint))
                            }
                        )
                    }
                    else -> {
                        SchoolView(school = schoolsList[index-3], onClickSchool)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
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
        ShowSchoolsScreen(
            schoolsList = schools,
            onClickSchool = {},
            onSatScoresFiltered = {_, _, _ -> }
        )
    }
}