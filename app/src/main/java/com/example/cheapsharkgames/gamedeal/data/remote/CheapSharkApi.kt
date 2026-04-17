package com.example.cheapsharkgames.gamedeal.data.remote

import com.example.cheapsharkgames.gamedeal.data.remote.CheapSharkApiEndpoints.DEALS_ENDPOINT
import com.example.cheapsharkgames.gamedeal.data.remote.CheapSharkApiEndpoints.GAMES_ENDPOINT
import com.example.cheapsharkgames.gamedeal.data.remote.dto.DealDto
import com.example.cheapsharkgames.gamedeal.data.remote.dto.GameDetailDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API interface for the CheapShark API.
 * Provides endpoints for fetching game deals and specific game details.
 *
 * Documentation: [https://apidocs.cheapshark.com/](https://apidocs.cheapshark.com/)
 */
interface CheapSharkApi {

    /**
     * Fetches a list of current game deals.
     *
     * @param storeId Optional ID of the store to filter deals by.
     * @param pageNumber The page number for pagination (0-indexed).
     * @param pageSize The number of results per page.
     * @return A list of [DealDto] objects representing the deals.
     */
    @GET(DEALS_ENDPOINT)
    suspend fun getDeals(
        @Query("storeID") storeId: String? = null,
        @Query("pageNumber") pageNumber: Int? = null,
        @Query("pageSize") pageSize: Int? = null,
    ): List<DealDto>

    /**
     * Fetches detailed information for a specific game.
     *
     * @param gameId The unique ID of the game.
     * @return A [GameDetailDto] containing game info, cheapest price history, and current deals.
     */
    @GET(GAMES_ENDPOINT)
    suspend fun getGameDetail(
        @Query("id") gameId: String,
    ): GameDetailDto
}
