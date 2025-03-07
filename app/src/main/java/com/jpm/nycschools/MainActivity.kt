package com.jpm.nycschools

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.jpm.nycschools.ui.MainScreen
import com.jpm.nycschools.viewmodels.NycSchoolsViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: NycSchoolsViewModel by viewModels{ NycSchoolsViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // try retrieving data only when the app is freshly launched,
        // and not on orientation changes
        if (savedInstanceState == null) {
            viewModel.retrieveRemoteData()
        }

        setContent {
            MaterialTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}