package com.example.newsapp.data.repository.source.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.data.repository.source.local.database.dao.ArticleDao
import com.example.newsapp.data.repository.source.local.database.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticleLocalDataSource @Inject constructor(
    private val articleDao: ArticleDao,
) {
    fun getBookmarkArticles(): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 2,
            ),
            pagingSourceFactory = { articleDao.getBookmarkArticles() }
        ).flow
    }

    suspend fun saveOrUpdateBookmark(article: ArticleData) {
        val articleEntity = ArticleEntity.fromArticleData(article) ?: return
        articleDao.saveOrUpdateBookmarkArticle(articleEntity)
    }

    suspend fun deleteBookmark(uuid: String) {
        articleDao.deleteBookmarkArticle(uuid)
    }

    suspend fun getBookmarkArticleById(articleId: String): ArticleData? {
        return articleDao.getBookmarkArticleById(articleId)?.run { ArticleData.fromArticleEntity(this) }
    }
}