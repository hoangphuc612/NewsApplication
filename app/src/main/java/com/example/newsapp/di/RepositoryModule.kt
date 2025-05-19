package com.example.newsapp.di

import com.example.newsapp.data.repository.ArticleRepositoryImpl
import com.example.newsapp.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideArticleRepository(articleRepositoryImpl: ArticleRepositoryImpl): ArticleRepository
}