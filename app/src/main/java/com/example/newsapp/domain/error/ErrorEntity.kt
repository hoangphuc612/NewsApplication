package com.example.newsapp.domain.error

abstract class ErrorEntity : Throwable() {
    abstract val originalThrowable: Throwable
}
