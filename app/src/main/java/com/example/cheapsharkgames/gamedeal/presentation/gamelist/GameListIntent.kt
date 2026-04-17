package com.example.cheapsharkgames.gamedeal.presentation.gamelist

/**
 * Represents user intentions or events on the Game List screen.
 */
sealed class GameListIntent {
    /**
     * Intent to load or refresh the list of games.
     */
    object LoadGames : GameListIntent()

    /**
     * Intent to load the next page of games for pagination.
     */
    object LoadNextPage : GameListIntent()

    /**
     * Intent triggered when a game item is clicked.
     * 
     * @property gameId The ID of the clicked game.
     */
    data class GameClicked(val gameId: String) : GameListIntent()
}
