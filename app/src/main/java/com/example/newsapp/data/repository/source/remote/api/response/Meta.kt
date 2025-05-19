package com.example.newsapp.data.repository.source.remote.api.response

import com.google.gson.annotations.Expose

data class Meta(
    @Expose
    val found: Int? = null,
    @Expose
    val returned: Int? = null,
    @Expose
    val limit: Int? = null,
    @Expose
    val page: Int? = null,
)

fun Meta.totalPage(): Int? = limit?.let { found?.div(it) }

fun Meta.nextPage(): Int? = page?.takeIf { it < (totalPage() ?: 0) }?.plus(1)
