package com.example.newsapp

import androidx.paging.PagingData
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.presentation.screens.bookmark.BookmarkViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.clearMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BookmarkViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var articleRepository: ArticleRepository
    private lateinit var viewModel: BookmarkViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        articleRepository = mockk()
        coEvery { articleRepository.getBookmarkArticles() } returns flowOf(PagingData.empty())
        viewModel = BookmarkViewModel(articleRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init calls getArticles which fetches bookmark articles from repository`() = testScope.runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 1) { articleRepository.getBookmarkArticles() }
    }

    @Test
    fun `getArticles fetches bookmark articles and updates articlesState`() = testScope.runTest {
        val mockArticle = ArticleData(uuid = "bookmark1", title = "Bookmarked Article")
        val pagingData = PagingData.from(listOf(mockArticle))
        val flowPagingData = flowOf(pagingData)
        coEvery { articleRepository.getBookmarkArticles() } returns flowPagingData
        clearMocks(articleRepository, recordedCalls = true, answers = false)
        viewModel.getArticles()
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { articleRepository.getBookmarkArticles() }
    }

    @Test
    fun `getArticles when repository throws error updates errorState via BaseViewModel`() = testScope.runTest {
        val testException = RuntimeException("Database error")
        coEvery { articleRepository.getBookmarkArticles() } throws testException
        clearMocks(articleRepository, recordedCalls = true, answers = false)
        coEvery { articleRepository.getBookmarkArticles() } throws testException
        viewModel.getArticles()
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(testException, viewModel.errorState.value.throwable)
        assertTrue(viewModel.errorState.value.shouldShowDialog)
        coVerify(exactly = 1) { articleRepository.getBookmarkArticles() }
    }
}