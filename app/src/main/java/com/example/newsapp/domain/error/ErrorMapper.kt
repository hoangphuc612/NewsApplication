package com.example.newsapp.domain.error

interface ErrorMapper {
    fun map(throwable: Throwable): ErrorEntity
}
