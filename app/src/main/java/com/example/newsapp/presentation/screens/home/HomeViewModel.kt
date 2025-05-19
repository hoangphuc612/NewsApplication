package com.example.newsapp.presentation.screens.home

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.domain.usecase.GetArticlesUseCase
import com.example.newsapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private val _articlesState = MutableStateFlow(PagingData.empty<ArticleData>())
    val articleDataState: Flow<PagingData<ArticleData>> = _articlesState.cachedIn(scope)

    init {
        _uiState.update { it.copy(isLoading = true) }
        getArticles()
    }

    override fun onRefresh() {
        super.onRefresh()
        getArticles()
    }

    fun onFinishedGetArticles() {
        if (isRefreshing.value) {
            onCancelRefresh()
        }
        _uiState.update { HomeUiState() }
    }

    fun onGetArticlesError(throwable: Throwable) {
        onError(throwable)
    }

    fun getArticles() {
        scope.launch {
            getArticlesUseCase().collectLatest { articles -> _articlesState.update { articles} }
        }
    }
}

data class HomeUiState(val isLoading: Boolean = false)
