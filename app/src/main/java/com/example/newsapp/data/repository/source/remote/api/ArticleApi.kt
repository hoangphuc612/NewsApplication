package com.example.newsapp.data.repository.source.remote.api

import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.data.repository.source.remote.api.response.BaseListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleApi {
    @GET("/v1/news/top")
    suspend fun getArticles(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null
    ): BaseListResponse<ArticleData>

    @GET("/v1/news/uuid/{uuid}")
    suspend fun getArticleDetail(@Path("uuid") uuid: String): ArticleData
}
