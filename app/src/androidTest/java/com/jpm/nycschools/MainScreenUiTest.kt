package com.jpm.nycschools

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.jpm.nycschools.datasources.NycSchoolsRemoteDataSource
import com.jpm.nycschools.models.SatScore
import com.jpm.nycschools.models.School
import com.jpm.nycschools.ui.MainScreen
import com.jpm.nycschools.viewmodels.NycSchoolsViewModel
import org.junit.Rule
import org.junit.Test

class MainScreenUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: NycSchoolsViewModel = NycSchoolsViewModel(NycSchoolsRemoteDataSource())

    @Test
    fun mainScreenTest() {

        val testDbn = "12344321"
        val testSchoolName = "Example School"

        val schools = listOf(
            School(
                dbn = "1",
                schoolName = "First School"
            ),
            School(
                dbn = testDbn,
                schoolName = testSchoolName
            ),
            School(
                dbn = "3",
                schoolName = "Last School"
            )
        )

        val sampleSatScore = SatScore(
            dbn = testDbn,
            schoolName = testSchoolName,
            satWritingAvgScore = "400",
            satMathAvgScore = "450",
            satCriticalReadingAvgScore = "324",
            numOfSatTestTakers = "23"
        )
        val satScoreList = listOf(
            sampleSatScore.copy(
                dbn = "1",
                schoolName = "First School"
            ),
            sampleSatScore,
            sampleSatScore.copy(
                dbn = "3",
                schoolName = "Last School"
            )
        )

        // Start the app
        composeTestRule.setContent {
            MaterialTheme {
                MainScreen(viewModel)
            }
        }

        // set the default UI, no data yet so this should go to the loading screen
        viewModel.refreshSchoolListUi()

        composeTestRule.onNodeWithText("Loading").assertIsDisplayed()

        // load the sample data into the ViewModel
        satScoreList.forEach { score ->
            viewModel.schoolsSatScores[score.dbn] = score
        }
        schools.forEach { school ->
            viewModel.schoolList[school.dbn] = school
        }
        viewModel.hasRetrievedData = Pair(true, true)

        // Data has now been loaded and should show the items on the screen
        viewModel.refreshSchoolListUi()

        // the "Loading" screen in no longer showing
        composeTestRule.onNodeWithText("Loading").assertIsNotDisplayed()
        // and we should see the test school now displayed
        composeTestRule.onNodeWithText(testSchoolName).assertIsDisplayed()
    }
}
