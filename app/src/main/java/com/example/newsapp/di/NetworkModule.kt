package com.example.newsapp.di

import com.example.newsapp.data.repository.source.remote.api.ArticleApi
import com.example.newsapp.data.repository.source.remote.api.helper.ApiConfig
import com.example.newsapp.data.repository.source.remote.api.helper.ServiceGenerator
import com.example.newsapp.data.repository.source.remote.api.interceptor.CommonInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @Singleton
    fun provideArticleService(gson: Gson): ArticleApi {
        return ServiceGenerator.generate(
            baseUrl = ApiConfig.baseUrl(),
            serviceClass = ArticleApi::class.java,
            gson = gson,
            authenticator = null,
            interceptors = arrayOf(
                CommonInterceptor()
            ),
            loggingInterceptor = HttpLoggingInterceptor(),
        )
    }
}
