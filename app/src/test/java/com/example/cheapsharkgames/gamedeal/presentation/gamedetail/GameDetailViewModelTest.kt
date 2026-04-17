package com.example.cheapsharkgames.gamedeal.presentation.gamedetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.cheapsharkgames.gamedeal.domain.model.CheapestPrice
import com.example.cheapsharkgames.gamedeal.domain.model.GameDeal
import com.example.cheapsharkgames.gamedeal.domain.model.GameDetail
import com.example.cheapsharkgames.gamedeal.domain.model.GameInfo
import com.example.cheapsharkgames.gamedeal.domain.usecase.GetGameDetailUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameDetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getGameDetailUseCase: GetGameDetailUseCase
    private lateinit var viewModel: GameDetailViewModel

    private val mockGameDetail = GameDetail(
        gameInfo = GameInfo(
            id = "1",
            name = "Test Game",
            thumbnail = "thumb_url"
        ),
        cheapestPriceEver = CheapestPrice(
            price = "5.00",
            date = 123456789L
        ),
        deals = listOf(
            GameDeal(
                storeId = "1",
                dealId = "deal1",
                price = "10.00",
                retailPrice = "20.00",
                savings = 50.0
            )
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getGameDetailUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when viewmodel starts with gameId, it should load game details`() = runTest {
        // Given
        val gameId = "1"
        val savedStateHandle = SavedStateHandle(mapOf("gameId" to gameId))
        every { getGameDetailUseCase(gameId) } returns flowOf(Result.success(mockGameDetail))

        // When
        viewModel = GameDetailViewModel(getGameDetailUseCase, savedStateHandle)

        // Let the init block coroutine run
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.gameDetail).isEqualTo(mockGameDetail)
            assertThat(state.isLoading).isFalse()
            assertThat(state.error).isNull()
        }
    }

    @Test
    fun `when loading game details fails, state should reflect error`() = runTest {
        // Given
        val gameId = "1"
        val errorMessage = "Network Error"
        val savedStateHandle = SavedStateHandle(mapOf("gameId" to gameId))
        every { getGameDetailUseCase(gameId) } returns flowOf(Result.failure(Exception(errorMessage)))

        // When
        viewModel = GameDetailViewModel(getGameDetailUseCase, savedStateHandle)

        // Let the init block coroutine run
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.gameDetail).isNull()
            assertThat(state.isLoading).isFalse()
            assertThat(state.error).isEqualTo(errorMessage)
        }
    }
}
