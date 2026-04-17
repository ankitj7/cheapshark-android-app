package com.example.cheapsharkgames.gamedeal.presentation.gamelist

import com.example.cheapsharkgames.gamedeal.domain.model.GameInfo

/**
 * State class for the Game List screen.
 *
 * @property isLoading Indicates if the first page is being fetched.
 * @property isNextPageLoading Indicates if a subsequent page is being fetched.
 * @property games The cumulative list of games to display.
 * @property error An error message if the fetch failed.
 * @property endOfPagination Reached the end of available data.
 */
data class GameListState(
    val isLoading: Boolean = false,
    val isNextPageLoading: Boolean = false,
    val games: List<GameInfo> = emptyList(),
    val error: String? = null,
    val endOfPagination: Boolean = false,
)
