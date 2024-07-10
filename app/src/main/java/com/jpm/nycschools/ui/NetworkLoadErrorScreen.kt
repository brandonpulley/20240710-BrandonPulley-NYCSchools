package com.jpm.nycschools.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jpm.nycschools.R
import com.jpm.nycschools.ui.components.HeaderText
import com.jpm.nycschools.ui.components.Label

@Composable
fun NetworkLoadErrorScreen(onClickRetry: () -> Unit, onClickLoadLocalData: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    ) {
        HeaderText(text = stringResource(id = R.string.network_load_error))
        Label(text = stringResource(id = R.string.network_load_error_msg))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickRetry
        ) {
            Text(text = stringResource(id = R.string.retry_network_request))
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickLoadLocalData
        ) {
            Text(text = stringResource(id = R.string.load_local_data))
        }
    }
}


@Preview
@Composable
fun NetworkLoadErrorScreenPreview() {
    MaterialTheme {
        NetworkLoadErrorScreen ({}, {})
    }
}