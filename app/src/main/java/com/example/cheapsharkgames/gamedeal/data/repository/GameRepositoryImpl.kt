package com.example.cheapsharkgames.gamedeal.data.repository

import com.example.cheapsharkgames.gamedeal.data.mapper.toDomain
import com.example.cheapsharkgames.gamedeal.data.mapper.toGameInfoDomain
import com.example.cheapsharkgames.gamedeal.data.remote.CheapSharkApi
import com.example.cheapsharkgames.gamedeal.domain.model.GameInfo
import com.example.cheapsharkgames.gamedeal.domain.model.GameDetail
import com.example.cheapsharkgames.gamedeal.domain.repository.GameRepository
import javax.inject.Inject

/**
 * Implementation of [GameRepository] that fetches data from the CheapShark API.
 */
class GameRepositoryImpl @Inject constructor(
    private val api: CheapSharkApi
) : GameRepository {

    override suspend fun getGames(page: Int): Result<List<GameInfo>> {
        return try {
            // Fetch deals for the specific page
            val games = api.getDeals(
                pageNumber = page,
            ).distinctBy { it.gameID }.map { it.toGameInfoDomain() }
            Result.success(games)
        } catch (e: Exception) {
            // TODO CheapShark - handle specific exceptions and API errors
            Result.failure(e)
        }
    }

    override suspend fun getGameDetail(gameId: String): Result<GameDetail> {
        return try {
            val detail = api.getGameDetail(gameId).toDomain(gameId)
            Result.success(detail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
