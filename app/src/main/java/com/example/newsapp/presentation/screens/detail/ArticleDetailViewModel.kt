package com.example.newsapp.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.data.repository.source.remote.error.ApiErrorMapper
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.domain.usecase.GetArticleDetailUseCase
import com.example.newsapp.presentation.base.BaseViewModel
import com.example.newsapp.presentation.navigation.ArticleDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val articleRepository: ArticleRepository,
    private val getArticleDetailUseCase: GetArticleDetailUseCase,
) : BaseViewModel() {

    private val articleUuid = savedStateHandle.toRoute<ArticleDetailRoute>().uuid

    private val _uiState = MutableStateFlow(ArticleDetailUiState())
    val uiState: StateFlow<ArticleDetailUiState> = _uiState

    init {
        getArticle()
        isBookmarked()
    }

    private fun getArticle() {
        _uiState.update { it.copy(isLoading = true) }
        scope.launch {
            getArticleDetailUseCase(GetArticleDetailUseCase.Params(articleUuid))
                .catch { throwable ->
                    _uiState.update { it.copy(isLoading = false) }
                    onError(ApiErrorMapper.map(throwable))
                }
                .collect { article ->
                    _uiState.update { it.copy(isLoading = false, article = article) }
                }
        }
    }

    private fun isBookmarked() {
        scope.launch {
            val isBookmarked = articleRepository.hasBookmarkArticle(articleUuid)
            _uiState.update { it.copy(isBookmarked = isBookmarked) }
        }
    }

    fun onBookmarkChanged() {
        if (uiState.value.isBookmarked) {
            unBookmarkArticle()
        } else {
            bookmarkArticle()
        }
    }

    private fun bookmarkArticle() {
        val article = _uiState.value.article ?: return
        scope.launch {
            articleRepository.saveOrUpdateBookmark(article)
            _uiState.update { it.copy(isBookmarked = true) }
        }
    }

    private fun unBookmarkArticle() {
        scope.launch {
            articleRepository.deleteBookmark(articleUuid)
            _uiState.update { it.copy(isBookmarked = false) }
        }
    }
}

data class ArticleDetailUiState(
    val isLoading: Boolean = false,
    val article: ArticleData? = null,
    val isBookmarked: Boolean = false,
)
