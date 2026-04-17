package com.example.cheapsharkgames.gamedeal.presentation.gamedetail

import com.example.cheapsharkgames.gamedeal.domain.model.GameDetail

/**
 * State class for the Game Detail screen.
 *
 * @property isLoading Indicates if the game details are currently being fetched.
 * @property gameDetail The detailed information about the game.
 * @property error An error message if the fetch failed.
 */
data class GameDetailState(
    val isLoading: Boolean = false,
    val gameDetail: GameDetail? = null,
    val error: String? = null
)
