package com.example.newsapp.presentation.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsapp.presentation.components.ArticleDescription
import com.example.newsapp.presentation.components.ArticleHeaderImage
import com.example.newsapp.presentation.components.ArticleTitle
import com.example.newsapp.presentation.components.BookmarkButton
import com.example.newsapp.presentation.components.Categories
import com.example.newsapp.presentation.components.LoadingBox
import com.example.newsapp.presentation.dialog.AppErrorDialog
import com.example.newsapp.presentation.theme.NewsAppTheme
import com.example.newsapp.presentation.utils.DateTimeUtils.formatIsoDateToDdMmYyyy

@Composable
fun ArticleDetailScreen(viewModel: ArticleDetailViewModel = hiltViewModel()) {

    val errorState by viewModel.errorState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (errorState.shouldShowDialog) {
        AppErrorDialog(
            throwable = errorState.throwable,
            onDismissRequest = viewModel::dismissErrorDialog,
        )
    }

    ArticleDetailContent(uiState, onBookmarkChanged = { viewModel.onBookmarkChanged() })
}

@Composable
fun ArticleDetailContent(
    articleDetailUiState: ArticleDetailUiState,
    onBookmarkChanged: () -> Unit,
) {

    LoadingBox(isLoading = articleDetailUiState.isLoading) {
        val article = articleDetailUiState.article
        if (article != null) {
            Column {
                ArticleHeaderImage(article.imageUrl)
                Box(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(12.dp))
                        Row {
                            ArticleTitle(
                                modifier = Modifier
                                    .fillMaxWidth((.8f)),
                                title = article.title ?: ""
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            BookmarkButton(
                                isBookmarked = articleDetailUiState.isBookmarked,
                                onClick = onBookmarkChanged,
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(formatIsoDateToDdMmYyyy(article.publishedAt), style = MaterialTheme.typography.labelSmall)
                        Spacer(modifier = Modifier.height(14.dp))
                        ArticleDescription(description = article.description ?: "")
                        Spacer(modifier = Modifier.height(12.dp))
                        Categories(categories = article.categories ?: emptyList())
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize())
        }
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    NewsAppTheme {
        Surface {
            ArticleDetailScreen()
        }
    }
}
