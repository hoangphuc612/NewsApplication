package com.example.newsapp.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.data.repository.source.local.ArticleLocalDataSource
import com.example.newsapp.data.repository.source.remote.ArticleRemoteDataSource
import com.example.newsapp.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleRemoteDataSource: ArticleRemoteDataSource,
    private val articleLocalDataSource: ArticleLocalDataSource,
): ArticleRepository {

    override fun getArticles(): Flow<PagingData<ArticleData>> {
        return articleRemoteDataSource.getArticles()
    }

    override fun getBookmarkArticles(): Flow<PagingData<ArticleData>> {
        return articleLocalDataSource.getBookmarkArticles().map {
            it.map { article ->
                ArticleData.fromArticleEntity(article)
            }
        }
    }

    override suspend fun saveOrUpdateBookmark(articleData: ArticleData) {
        articleLocalDataSource.saveOrUpdateBookmark(articleData)
    }

    override suspend fun deleteBookmark(uuid: String) {
        articleLocalDataSource.deleteBookmark(uuid)
    }

    override suspend fun hasBookmarkArticle(uuid: String): Boolean {
        return articleLocalDataSource.getBookmarkArticleById(uuid) != null
    }

    override suspend fun getBookmarkArticleById(articleId: String): ArticleData? {
        return articleLocalDataSource.getBookmarkArticleById(articleId)
    }

    override fun getArticleDetail(uuid: String): Flow<ArticleData> {
        return articleRemoteDataSource.getArticleDetail(uuid)
    }
}
