package com.example.cheapsharkgames.gamedeal.presentation.gamelist

import com.example.cheapsharkgames.gamedeal.domain.model.GameInfo

/**
 * State class for the Game List screen.
 *
 * @property isLoading Indicates if the games are currently being fetched.
 * @property games The list of games to display.
 * @property error An error message if the fetch failed.
 */
data class GameListState(
    val isLoading: Boolean = false,
    val games: List<GameInfo> = emptyList(),
    val error: String? = null // TODO CheapShark - create a global Error data class and use it here
)
