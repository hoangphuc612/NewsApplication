package com.example.newsapp.presentation.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.NewsProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .align(Alignment.Center)
            .size(80.dp),
        color = MaterialTheme.colorScheme.tertiary,
    )
}
