package com.example.newsapp.data.repository.source.remote.api.interceptor

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.repository.source.remote.api.helper.ApiConfig.API_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class CommonInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(API_TOKEN, BuildConfig.API_TOKEN)
            .build()

        val newRequest = originalRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}