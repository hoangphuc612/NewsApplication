package com.example.newsapp.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Creates a _cold_ flow that produces values from the given Throwable.
 */
fun <T> Throwable.asFlow(): Flow<T> {
    return flow {
        emit(
            suspendCancellableCoroutine { continuation ->
                continuation.cancel(this@asFlow)
            }
        )
    }
}

/**
 * Creates a _cold_ flow that produces values from the given T type.
 */
fun <T> T.asFlow(): Flow<T> = flow {
    emit(this@asFlow)
}
