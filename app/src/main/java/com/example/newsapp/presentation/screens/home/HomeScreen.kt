package com.example.newsapp.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.presentation.components.LoadingBox
import com.example.newsapp.presentation.components.articlesUi
import com.example.newsapp.presentation.dialog.AppErrorDialog
import com.example.newsapp.presentation.theme.NewsAppTheme

@Composable
fun HomeScreen(
    navigateToDetail: (id: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val articlePagingItems = viewModel.articleDataState.collectAsLazyPagingItems()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorState by viewModel.errorState.collectAsStateWithLifecycle()

    if (errorState.shouldShowDialog) {
        viewModel.onFinishedGetArticles()
        AppErrorDialog(
            throwable = errorState.throwable,
            onDismissRequest = viewModel::dismissErrorDialog,
        )
    }

    LaunchedEffect(articlePagingItems.loadState) {
        when {
            articlePagingItems.loadState.refresh is LoadState.NotLoading -> {
                viewModel.onFinishedGetArticles()
            }

            articlePagingItems.loadState.append is LoadState.Error -> {
                viewModel.onGetArticlesError(
                    (articlePagingItems.loadState.append as LoadState.Error).error
                )
            }

            articlePagingItems.loadState.refresh is LoadState.Error -> {
                viewModel.onGetArticlesError(
                    (articlePagingItems.loadState.append as LoadState.Error).error
                )
            }
        }
    }

    HomeScreenContent(
        uiState = uiState,
        isRefreshing = isRefreshing,
        articlePagingItems = articlePagingItems,
        onArticleClick = { navigateToDetail(it) },
        onRefresh = viewModel::onRefresh
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    isRefreshing: Boolean,
    articlePagingItems: LazyPagingItems<ArticleData>,
    onArticleClick: (String) -> Unit,
    onRefresh: () -> Unit
) {
    LoadingBox(isLoading = uiState.isLoading) {
        PullToRefreshBox(
            state = rememberPullToRefreshState(),
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
        ) {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier.fillMaxSize()
            ) {
                articlesUi(
                    articles = articlePagingItems,
                    onArticleClick = { onArticleClick(it) },
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    NewsAppTheme {
        Surface {
            HomeScreen(navigateToDetail = {})
        }
    }
}
