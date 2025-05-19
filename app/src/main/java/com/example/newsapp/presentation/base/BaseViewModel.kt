package com.example.newsapp.presentation.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus

open class BaseViewModel : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _errorState = MutableStateFlow(ErrorState())
    val errorState = _errorState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorState.update { it.copy(throwable = throwable, shouldShowDialog = true) }
    }

    open val scope = viewModelScope.plus(exceptionHandler)

    @CallSuper
    open fun onRefresh() {
        _isRefreshing.update { true }
    }

    fun onCancelRefresh() {
        _isRefreshing.update { false }
    }

    protected fun onError(throwable: Throwable) {
        onCancelRefresh()
        _errorState.update {
            it.copy(throwable = throwable, shouldShowDialog = true)
        }
    }

    fun dismissErrorDialog() {
        _errorState.update { it.copy(throwable = null, shouldShowDialog = false) }
    }
}

data class ErrorState(
    val throwable: Throwable? = null,
    val shouldShowDialog: Boolean = false,
)