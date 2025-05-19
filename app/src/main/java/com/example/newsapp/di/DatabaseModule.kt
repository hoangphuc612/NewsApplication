package com.example.newsapp.di

import android.app.Application
import androidx.room.Room
import com.example.newsapp.data.repository.source.local.database.ArticleDatabase
import com.example.newsapp.data.repository.source.local.database.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): ArticleDatabase {
        return Room.databaseBuilder(application, ArticleDatabase::class.java, ARTICLE_DATABASE)
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }

    companion object {
        private const val ARTICLE_DATABASE = "NewsApplication.db"
    }
}
