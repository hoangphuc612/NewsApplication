package com.example.newsapp.data.repository.source.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.repository.source.local.database.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateBookmarkArticle(article: ArticleEntity)

    @Query("SELECT * FROM articles")
    fun getBookmarkArticles(): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM articles WHERE uuid = :articleId")
    suspend fun deleteBookmarkArticle(articleId: String)

    @Query("SELECT * FROM articles WHERE uuid = :articleId")
    suspend fun getBookmarkArticleById(articleId: String): ArticleEntity?
}