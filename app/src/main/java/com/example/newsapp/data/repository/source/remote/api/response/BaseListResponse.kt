package com.example.newsapp.data.repository.source.remote.api.response

import com.google.gson.annotations.Expose

data class BaseListResponse<T>(
    @Expose
    val data: List<T>? = null,
    @Expose
    val meta: Meta? = null,
)