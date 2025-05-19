package com.example.newsapp

import androidx.paging.PagingData
import com.example.newsapp.domain.usecase.GetArticlesUseCase
import com.example.newsapp.presentation.screens.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
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
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var getArticlesUseCase: GetArticlesUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getArticlesUseCase = mockk()
        coEvery { getArticlesUseCase(any()) } returns flowOf(PagingData.empty()) // `any()` because params is nullable Void?
        viewModel = HomeViewModel(getArticlesUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init sets uiState isLoading to true and calls getArticles`() = testScope.runTest {
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue("isLoading should be true after init", viewModel.uiState.value.isLoading)
        coVerify(exactly = 1) { getArticlesUseCase(null) }
    }

    @Test
    fun `onFinishedGetArticles sets uiState isLoading to false and cancels refresh if refreshing`() = testScope.runTest {
        viewModel.onRefresh()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.isRefreshing.value)
        viewModel.onFinishedGetArticles()

        assertFalse("isLoading should be false after onFinishedGetArticles", viewModel.uiState.value.isLoading)
        assertFalse("isRefreshing should be false after onFinishedGetArticles", viewModel.isRefreshing.value)
    }

    @Test
    fun `onFinishedGetArticles sets uiState isLoading to false when not refreshing`() = testScope.runTest {
        viewModel.onCancelRefresh() // from BaseViewModel, sets isRefreshing to false
        assertFalse(viewModel.isRefreshing.value)
        assertTrue("isLoading should be true initially (from init)", viewModel.uiState.value.isLoading)
        viewModel.onFinishedGetArticles()
        assertFalse("isLoading should be false after onFinishedGetArticles", viewModel.uiState.value.isLoading)
        assertFalse("isRefreshing should remain false", viewModel.isRefreshing.value)
    }

    @Test
    fun `onGetArticlesError updates errorState and cancels refresh`() = testScope.runTest {
        val testException = RuntimeException("Test Network Error")
        viewModel.onRefresh()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.isRefreshing.value)
        viewModel.onGetArticlesError(testException)
        assertEquals(testException, viewModel.errorState.value.throwable)
        assertTrue(viewModel.errorState.value.shouldShowDialog)
        assertFalse("isRefreshing should be false after error", viewModel.isRefreshing.value)
    }

    @Test
    fun `dismissErrorDialog resets errorState`() = testScope.runTest {
        val testException = RuntimeException("Initial Error")
        viewModel.onGetArticlesError(testException)
        assertNotNull(viewModel.errorState.value.throwable)
        assertTrue(viewModel.errorState.value.shouldShowDialog)
        viewModel.dismissErrorDialog()
        assertNull(viewModel.errorState.value.throwable)
        assertFalse(viewModel.errorState.value.shouldShowDialog)
    }
}