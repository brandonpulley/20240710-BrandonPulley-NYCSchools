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
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NetworkLoadErrorScreen(onClickRetry: () -> Unit, onClickLoadLocalData: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
    ) {
        HeaderText(text = "Network Load Error")
        Label(text = "There was an error when retrieving data from the network. You can try refreshing or you can load the data that was bundled with the app")
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickRetry
        ) {
            Text(text = "Retry network request")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onClickLoadLocalData
        ) {
            Text(text = "Load local data")
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