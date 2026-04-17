package com.example.cheapsharkgames.gamedeal.domain.usecase

import com.example.cheapsharkgames.gamedeal.domain.model.GameInfo
import com.example.cheapsharkgames.gamedeal.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Use case to fetch a list of game deals with pagination support.
 *
 * @property repository The [GameRepository] to fetch data from.
 */
class GetGamesUseCase @Inject constructor(
    private val repository: GameRepository
) {
    /**
     * Executes the use case.
     *
     * @param page The page number to fetch.
     * @return A [Flow] emitting a [Result] containing a list of [GameInfo].
     */
    operator fun invoke(page: Int): Flow<Result<List<GameInfo>>> {
        return flow {
            val games = repository.getGames(page)
            emit(games)
        }.flowOn(Dispatchers.IO)
    }
}
