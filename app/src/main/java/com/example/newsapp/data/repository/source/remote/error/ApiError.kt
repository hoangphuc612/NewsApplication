package com.example.newsapp.data.repository.source.remote.error

import com.example.newsapp.domain.error.ErrorEntity
import com.example.newsapp.data.repository.source.remote.response.BaseErrorResponse

sealed class ApiError : ErrorEntity() {

    data class HttpError(
        override val originalThrowable: Throwable,
        val errorResponse: BaseErrorResponse?,
    ) : ApiError()

    data class ServerError(
        override val originalThrowable: Throwable,
        val errorResponse: BaseErrorResponse?,
    ) : ApiError()

    data class NetworkError(override val originalThrowable: Throwable) : ApiError()

    data class UnexpectedError(override val originalThrowable: Throwable) : ApiError()
}
