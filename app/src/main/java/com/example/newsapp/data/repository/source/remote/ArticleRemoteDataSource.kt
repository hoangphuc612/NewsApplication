package com.example.newsapp.data.repository.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.data.repository.source.remote.api.ArticleApi
import com.example.newsapp.data.repository.source.remote.paging.ArticlesPagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ArticleRemoteDataSource @Inject constructor(
    private val articleApi: ArticleApi,
) {

    fun getArticles(): Flow<PagingData<ArticleData>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 3,
                initialLoadSize = 1,
                prefetchDistance = 1,
            ),
            pagingSourceFactory = { ArticlesPagingSource(articleApi) },
        ).flow
    }

    fun getArticleDetail(uuid: String): Flow<ArticleData> = flow {
        val article = articleApi.getArticleDetail(uuid)
        emit(article)
    }
}