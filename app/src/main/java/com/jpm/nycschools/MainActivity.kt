package com.jpm.nycschools

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import com.jpm.nycschools.ui.MainScreen
import com.jpm.nycschools.viewmodels.NycSchoolsViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: NycSchoolsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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