package com.jpm.nycschools.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Label(text: String) {
    Text(
        modifier = Modifier.padding(start = 6.dp, end = 6.dp),
        text = text,
        color = Color.White,
        style = MaterialTheme.typography.bodyLarge
    )
}

