package com.example.newsapp.data.repository.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.data.repository.source.remote.api.ArticleApi
import com.example.newsapp.data.repository.source.remote.api.helper.execute
import com.example.newsapp.data.repository.source.remote.api.response.nextPage
import com.example.newsapp.data.repository.source.remote.error.ApiErrorMapper

class ArticlesPagingSource(
    private val articleApi: ArticleApi,
): PagingSource<Int, ArticleData>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleData> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = articleApi.execute { getArticles(page = nextPageNumber, limit = 3) }

            LoadResult.Page(
                data = response.data.orEmpty().filter { !it.imageUrl.isNullOrEmpty() },
                prevKey = null,
                nextKey = response.meta?.nextPage()
            )
        } catch (e: Exception) {
            LoadResult.Error(ApiErrorMapper.map(e))
        }
    }
}