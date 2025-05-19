package com.example.newsapp.data.repository.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsapp.data.repository.source.local.database.dao.ArticleDao
import com.example.newsapp.data.repository.source.local.database.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
