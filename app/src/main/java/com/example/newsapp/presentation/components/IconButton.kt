package com.example.newsapp.presentation.components

import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.presentation.theme.NewsAppTheme
import com.example.newsapp.presentation.theme.NewsIcon

@Composable
fun NewsIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    checkedIcon: @Composable () -> Unit = icon,
) {
    FilledIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconToggleButtonColors(
            checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = if (checked) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.12f
                )
            } else {
                Color.Transparent
            },
        ),
    ) {
        if (checked) checkedIcon() else icon()
    }
}

@Preview
@Composable
fun NewsIconToggleButtonPreview() {
    NewsAppTheme {
        Surface {
            NewsIconToggleButton(
                checked = true,
                icon = {
                    Icon(
                        imageVector = NewsIcon.BookmarkBorder,
                        contentDescription = null,
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = NewsIcon.Bookmark,
                        contentDescription = null,
                    )
                },
                onCheckedChange = {}
            )
        }
    }
}

@Preview
@Composable
fun NewsIconToggleButtonPreviewUnChecked() {
    NewsAppTheme {
        Surface {
            NewsIconToggleButton(
                checked = false,
                icon = {
                    Icon(
                        imageVector = NewsIcon.BookmarkBorder,
                        contentDescription = null,
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = NewsIcon.Bookmark,
                        contentDescription = null,
                    )
                },
                onCheckedChange = {}
            )
        }
    }
}
