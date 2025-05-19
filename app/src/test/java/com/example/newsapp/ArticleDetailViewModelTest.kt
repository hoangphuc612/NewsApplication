package com.example.newsapp

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.newsapp.data.model.ArticleData
import com.example.newsapp.data.repository.source.remote.error.ApiError
import com.example.newsapp.domain.repository.ArticleRepository
import com.example.newsapp.domain.usecase.GetArticleDetailUseCase
import com.example.newsapp.presentation.screens.detail.ArticleDetailUiState
import com.example.newsapp.presentation.screens.detail.ArticleDetailViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ArticleDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var articleRepository: ArticleRepository
    private lateinit var getArticleDetailUseCase: GetArticleDetailUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    private val uuid = "test-uuid"
    private val article = ArticleData(
        uuid = uuid,
        title = "Sample Title",
        description = "Sample Description"
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        articleRepository = mockk(relaxed = true)
        getArticleDetailUseCase = mockk()
        savedStateHandle = SavedStateHandle(mapOf("uuid" to uuid))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun buildViewModel() = ArticleDetailViewModel(
        savedStateHandle,
        articleRepository,
        getArticleDetailUseCase
    )

    @Test
    fun `initial state emits loading then article loaded and isBookmarked false`() = testScope.runTest {
        coEvery { articleRepository.hasBookmarkArticle(uuid) } returns false
        every { getArticleDetailUseCase(any()) } returns flowOf(article)

        val viewModel = buildViewModel()

        viewModel.uiState.test {
            assertEquals(ArticleDetailUiState(isLoading = true), awaitItem())
            val loadedState = awaitItem()
            assertFalse(loadedState.isLoading)
            assertEquals(article, loadedState.article)
            assertFalse(loadedState.isBookmarked)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getArticle emits error updates errorState and stops loading`() = testScope.runTest {
        val throwable = RuntimeException("Network Error")
        coEvery { articleRepository.hasBookmarkArticle(uuid) } returns false
        every { getArticleDetailUseCase(any()) } returns flow { throw throwable }

        val viewModel = buildViewModel()

        // Assert uiState for loading then not loading
        viewModel.uiState.test {
            assertEquals(ArticleDetailUiState(isLoading = true), awaitItem())
            val stateAfterError = awaitItem()
            assertFalse(stateAfterError.isLoading)
            assertNull(stateAfterError.article)
            assertFalse(stateAfterError.isBookmarked)
            cancelAndIgnoreRemainingEvents()
        }

        // Assert errorState for mapped error
        viewModel.errorState.test {
            val error = awaitItem() // Only expect one emission, which is the error!
            assertTrue(error.throwable is ApiError.UnexpectedError)
            assertEquals(throwable, (error.throwable as ApiError.UnexpectedError).originalThrowable)
            assertTrue(error.shouldShowDialog)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `dismissErrorDialog resets errorState`() = testScope.runTest {
        val throwable = RuntimeException("error!")
        coEvery { articleRepository.hasBookmarkArticle(uuid) } returns false
        every { getArticleDetailUseCase(any()) } returns flow { throw throwable }

        val viewModel = buildViewModel()

        viewModel.errorState.test {
            awaitItem()
            val error = awaitItem()
            assertTrue(error.shouldShowDialog)
            viewModel.dismissErrorDialog()
            val dismissed = awaitItem()
            assertNull(dismissed.throwable)
            assertFalse(dismissed.shouldShowDialog)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onRefresh sets isRefreshing true and onCancelRefresh resets it`() = testScope.runTest {
        coEvery { articleRepository.hasBookmarkArticle(uuid) } returns false
        every { getArticleDetailUseCase(any()) } returns flowOf(article)
        val viewModel = buildViewModel()

        viewModel.isRefreshing.test {
            assertFalse(awaitItem())
            viewModel.onRefresh()
            assertTrue(awaitItem())
            viewModel.onCancelRefresh()
            assertFalse(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}