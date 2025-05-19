package com.example.newsapp.presentation.screens.bookmark

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.presentation.components.LoadingBox
import com.example.newsapp.presentation.components.articlesUi
import com.example.newsapp.presentation.theme.NewsAppTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun BookmarkScreen(
    navigateToDetail: (String) -> Unit,
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    val articlePagingItems = viewModel.articleDataState.collectAsLazyPagingItems()
    val isLoading = articlePagingItems.loadState.refresh is LoadState.Loading

    LaunchedEffect(Unit) {
        viewModel.getArticles()
    }

    BookmarkScreenContent(isLoading = isLoading, articlePagingItems) {
        navigateToDetail(it)
    }
}

@Composable
fun BookmarkScreenContent(
    isLoading: Boolean,
    articlePagingItems: LazyPagingItems<ArticleData>,
    onArticleClick: (String) -> Unit,
) {
    LoadingBox(isLoading = isLoading) {
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier.fillMaxSize()
        ) {
            articlesUi(
                articles = articlePagingItems,
                onArticleClick = {
                    onArticleClick(it)
                },
            )
        }
    }
}

@Preview
@Composable
fun BookmarkScreenContentPreview() {
    NewsAppTheme {
        Surface {
            BookmarkScreenContent(
                isLoading = false,
                articlePagingItems = flowOf(PagingData.from(listOf(ArticleData()))).collectAsLazyPagingItems(),
            ) { }
        }
    }
}
