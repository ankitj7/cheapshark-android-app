package com.example.cheapsharkgames.gamedeal.presentation.gamedetail

/**
 * Represents user intentions or events on the Game Detail screen.
 */
sealed class GameDetailIntent {
    /**
     * Intent to load details for a specific game.
     * 
     * @property gameId The unique identifier of the game.
     */
    data class LoadGameDetail(val gameId: String) : GameDetailIntent()
}
