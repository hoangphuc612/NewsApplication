package com.example.newsapp.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.R
import com.example.newsapp.presentation.navigation.ArticleDetailRoute
import com.example.newsapp.presentation.navigation.HomeRoute

@Composable
fun rememberNewsAppState(navController: NavHostController = rememberNavController()): NewsAppState {
    return remember(navController) {
        NewsAppState(navController = navController)
    }
}

@Stable
class NewsAppState(val navController: NavHostController) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentRouteComposable: String?
        @Composable get() = getCurrentRoute(currentDestination?.route)

    fun getCurrentRoute(route: String?) = route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")

    @Composable
    fun getScreenTitle(): String {
        return when (currentRouteComposable) {
            HomeRoute::class.simpleName -> stringResource(R.string.home)
            ArticleDetailRoute::class.simpleName -> stringResource(R.string.detail)
            else -> stringResource(R.string.bookmark)
        }
    }
}