package com.example.newsapp.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
object BookmarkRoute

fun NavController.navigateToBookmark(navOptions: NavOptions? = null) {
    navigate(
        route = BookmarkRoute,
        navOptions = navOptions
    )
}
