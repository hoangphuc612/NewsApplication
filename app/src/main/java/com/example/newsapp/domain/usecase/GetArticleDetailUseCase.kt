package com.example.newsapp.domain.usecase

import android.content.Context
import com.example.newsapp.R
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.data.repository.source.remote.error.ApiError
import com.example.newsapp.di.IoDispatcher
import com.example.newsapp.domain.asFlow
import com.example.newsapp.domain.repository.ArticleRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleDetailUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context
): UseCase<GetArticleDetailUseCase.Params, ArticleData>(dispatcher) {

    override fun execute(params: Params?): Flow<ArticleData> {
        if (!params?.uuid.isNullOrBlank()) {
            return articleRepository.getArticleDetail(params!!.uuid)
        }
        return ApiError.UnexpectedError(originalThrowable = Throwable(message = context.getString(R.string.uuid_invalid))).asFlow()
    }

    data class Params(val uuid: String)
}