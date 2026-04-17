package com.example.cheapsharkgames.gamedeal.domain.usecase

import com.example.cheapsharkgames.gamedeal.domain.model.GameDetail
import com.example.cheapsharkgames.gamedeal.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Use case to fetch detailed information for a specific game.
 *
 * @property repository The [GameRepository] to fetch data from.
 */
class GetGameDetailUseCase @Inject constructor(
    private val repository: GameRepository
) {
    /**
     * Executes the use case.
     *
     * @param gameId The unique ID of the game to fetch details for.
     * @return A [Flow] emitting a [Result] containing the [GameDetail].
     */
    operator fun invoke(gameId: String): Flow<Result<GameDetail>> {
        return flow {
            val gameDetail = repository.getGameDetail(gameId)
            emit(gameDetail)
        }.flowOn(Dispatchers.IO)
    }
}
