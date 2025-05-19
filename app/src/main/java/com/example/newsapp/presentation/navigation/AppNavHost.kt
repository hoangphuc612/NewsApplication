package com.example.newsapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsapp.presentation.NewsAppState
import com.example.newsapp.presentation.screens.bookmark.BookmarkScreen
import com.example.newsapp.presentation.screens.detail.ArticleDetailScreen
import com.example.newsapp.presentation.screens.home.HomeScreen

@Composable
fun AppNavHost(appState: NewsAppState, modifier: Modifier) {
    val navController = appState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute::class
    ) {
        composable<HomeRoute> {
            HomeScreen(
                navigateToDetail = {
                    navController.navigateToArticleDetail(it)
                },
            )
        }
        composable<BookmarkRoute> {
            BookmarkScreen(
                navigateToDetail = {
                    navController.navigateToArticleDetail(it)
                },
            )
        }
        composable<ArticleDetailRoute> {
            ArticleDetailScreen()
        }
    }
}