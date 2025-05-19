package com.example.newsapp.domain.repository

import androidx.paging.PagingData
import com.example.newsapp.data.model.ArticleData
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticles(): Flow<PagingData<ArticleData>>

    fun getBookmarkArticles(): Flow<PagingData<ArticleData>>

    suspend fun saveOrUpdateBookmark(articleData: ArticleData)

    suspend fun deleteBookmark(uuid: String)

    suspend fun getBookmarkArticleById(articleId: String): ArticleData?

    suspend fun hasBookmarkArticle(uuid: String): Boolean

    fun getArticleDetail(uuid: String): Flow<ArticleData>
}