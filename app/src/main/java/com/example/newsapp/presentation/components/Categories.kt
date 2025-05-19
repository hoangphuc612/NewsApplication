package com.example.newsapp.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.presentation.theme.NewsAppTheme
import java.util.Locale

@Composable
fun Categories(
    categories: List<String>,
    modifier: Modifier = Modifier,
) {
    Row(
        // causes narrow chips
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (category in categories) {
            CategoryTag(
                text = { Text(text = category.uppercase(Locale.getDefault())) },
            )
        }
    }
}

@Preview
@Composable
fun CategoriesPreview() {
    NewsAppTheme {
        Surface {
            Categories(
                categories = listOf("general", "business")
            )
        }
    }
}
