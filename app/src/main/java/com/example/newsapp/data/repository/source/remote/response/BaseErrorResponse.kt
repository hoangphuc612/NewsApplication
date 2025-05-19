package com.example.newsapp.data.repository.source.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseErrorResponse(
    @Expose @SerializedName("error") val error: ErrorResponse? = null
)

data class ErrorResponse(
    @Expose @SerializedName("code") val code: String? = null,
    @Expose @SerializedName("message") val message: String? = null,
)