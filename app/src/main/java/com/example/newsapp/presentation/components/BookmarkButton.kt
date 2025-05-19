package com.example.newsapp.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.presentation.theme.NewsAppTheme
import com.example.newsapp.presentation.theme.NewsIcon

@Composable
fun BookmarkButton(
    isBookmarked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NewsIconToggleButton(
        checked = isBookmarked,
        onCheckedChange = { onClick() },
        modifier = modifier,
        icon = {
            Icon(
                imageVector = NewsIcon.BookmarkBorder,
                contentDescription = "Bookmark",
            )
        },
        checkedIcon = {
            Icon(
                imageVector = NewsIcon.Bookmark,
                contentDescription = "Unbookmark",
            )
        },
    )
}

@Preview("Bookmark Button")
@Composable
private fun BookmarkButtonPreview() {
    NewsAppTheme {
        Surface {
            BookmarkButton(isBookmarked = false, onClick = { })
        }
    }
}

@Preview("Bookmark Button Bookmarked")
@Composable
private fun BookmarkButtonBookmarkedPreview() {
    NewsAppTheme {
        Surface {
            BookmarkButton(isBookmarked = true, onClick = { })
        }
    }
}