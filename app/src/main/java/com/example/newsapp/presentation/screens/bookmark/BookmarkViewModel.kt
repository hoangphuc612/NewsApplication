package com.example.newsapp.presentation.screens.bookmark

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
): BaseViewModel() {
    private val _articlesState = MutableStateFlow(PagingData.empty<ArticleData>())
    val articleDataState: Flow<PagingData<ArticleData>> = _articlesState.cachedIn(viewModelScope)

    init {
        getArticles()
    }

    fun getArticles() {
        scope.launch {
            articleRepository.getBookmarkArticles().collectLatest { articles ->
                _articlesState.update { articles }
            }
        }
    }
}