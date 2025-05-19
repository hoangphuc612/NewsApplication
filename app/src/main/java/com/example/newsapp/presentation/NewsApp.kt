package com.example.newsapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.presentation.navigation.AppNavHost
import com.example.newsapp.presentation.navigation.HomeRoute
import com.example.newsapp.presentation.navigation.navigateToBookmark
import com.example.newsapp.presentation.theme.NewsIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsApp(appState: NewsAppState) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(appState.getScreenTitle())
                },
                navigationIcon = {
                    if (appState.currentRouteComposable != HomeRoute::class.simpleName) {
                        Icon(
                            modifier = Modifier.clickable {
                                appState.navController.navigateUp()
                            },
                            imageVector = NewsIcon.ArrowBack,
                            contentDescription = "Back Button",
                        )
                    }
                },
                actions = {
                    if (appState.currentRouteComposable == HomeRoute::class.simpleName) {
                        Icon(
                            modifier = Modifier.clickable {
                                appState.navController.navigateToBookmark()
                            },
                            imageVector = NewsIcon.Bookmarks,
                            contentDescription = "Saved history button",
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        AppNavHost(modifier = Modifier.padding(innerPadding), appState = appState)
    }
}