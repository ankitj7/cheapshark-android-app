package com.example.cheapsharkgames.gamedeal.domain.repository

import com.example.cheapsharkgames.gamedeal.domain.model.GameInfo
import com.example.cheapsharkgames.gamedeal.domain.model.GameDetail

/**
 * Repository interface for managing game-related data.
 * This interface abstracts the data source (e.g., Network, Database) from the domain layer.
 */
interface GameRepository {

    /**
     * Fetches a list of current game deals.
     *
     * @param page The page number to fetch.
     * @return A [Result] containing a list of [GameInfo] objects on success, or an exception on failure.
     */
    suspend fun getGames(page: Int): Result<List<GameInfo>>

    /**
     * Fetches detailed information for a specific game.
     *
     * @param gameId The unique identifier of the game.
     * @return A [Result] containing [GameDetail] on success, or an exception on failure.
     */
    suspend fun getGameDetail(gameId: String): Result<GameDetail>
}
