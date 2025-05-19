package com.example.newsapp.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDetailRoute(val uuid: String)

fun NavController.navigateToArticleDetail(uuid: String, navOptions: NavOptions? = null) {
    navigate(
        route = ArticleDetailRoute(uuid),
        navOptions = navOptions
    )
}