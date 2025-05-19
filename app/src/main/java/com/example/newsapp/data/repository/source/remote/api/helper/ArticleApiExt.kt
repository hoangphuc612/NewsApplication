package com.example.newsapp.data.repository.source.remote.api.helper

import com.example.newsapp.data.repository.source.remote.api.ArticleApi
import com.example.newsapp.data.repository.source.remote.error.ApiErrorMapper

inline fun <R> ArticleApi.execute(block: ArticleApi.() -> R): R {
    try {
        return block()
    } catch (throwable: Throwable) {
        throw ApiErrorMapper.map(throwable)
    }
}

