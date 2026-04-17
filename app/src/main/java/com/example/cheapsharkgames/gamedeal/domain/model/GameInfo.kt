package com.example.cheapsharkgames.gamedeal.domain.model

/**
 * Represents a simplified game model used for displaying in lists.
 *
 * @property id The unique identifier of the game (derived from gameID in the API).
 * @property name The display title of the game.
 * @property thumbnail URL to the game's thumbnail image.
 */
data class GameInfo(
    val id: String,
    val name: String,
    val thumbnail: String
)
