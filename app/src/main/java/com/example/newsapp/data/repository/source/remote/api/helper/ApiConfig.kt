package com.example.newsapp.data.repository.source.remote.api.helper

object ApiConfig {

    const val API_TOKEN = "api_token"

    fun baseUrl(): String {
        return "https://api.thenewsapi.com/"
    }
}
