package com.example.newsapp.domain.usecase

import androidx.paging.PagingData
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.di.IoDispatcher
import com.example.newsapp.domain.repository.ArticleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
): UseCase<Void?, PagingData<ArticleData>>(dispatcher) {

    override fun execute(params: Void?): Flow<PagingData<ArticleData>> {
        return articleRepository.getArticles()
    }
}