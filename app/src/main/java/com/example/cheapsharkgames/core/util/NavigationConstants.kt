package com.example.cheapsharkgames.core.util

/**
 * Constants used for Jetpack Navigation routes and arguments.
 */
object NavigationConstants {
    /**
     * Route for the Game List screen.
     */
    const val ROUTE_GAME_LIST = "game_list"

    /**
     * Route template for the Game Detail screen.
     */
    const val ROUTE_GAME_DETAIL = "game_detail/{gameId}"

    /**
     * Argument name for the game ID.
     */
    const val ARG_GAME_ID = "gameId"

    /**
     * Helper function to build the navigation path for a specific game detail.
     */
    fun gameDetailPath(gameId: String) = "game_detail/$gameId"
}
