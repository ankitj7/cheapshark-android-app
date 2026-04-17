package com.example.cheapsharkgames.gamedeal.data.mapper

import com.example.cheapsharkgames.gamedeal.data.remote.dto.CheapestPriceDto
import com.example.cheapsharkgames.gamedeal.data.remote.dto.DealDto
import com.example.cheapsharkgames.gamedeal.data.remote.dto.DealSummaryDto
import com.example.cheapsharkgames.gamedeal.data.remote.dto.GameDetailDto
import com.example.cheapsharkgames.gamedeal.data.remote.dto.GameInfoDto
import com.example.cheapsharkgames.gamedeal.domain.model.CheapestPrice
import com.example.cheapsharkgames.gamedeal.domain.model.GameInfo
import com.example.cheapsharkgames.gamedeal.domain.model.GameDeal
import com.example.cheapsharkgames.gamedeal.domain.model.GameDetail

/**
 * Maps [DealDto] from the data layer to [GameInfo] in the domain layer.
 */
fun DealDto.toGameInfoDomain() = GameInfo(
    id = gameID,
    name = title,
    thumbnail = thumb
)

/**
 * Maps [GameDetailDto] from the data layer to [GameDetail] in the domain layer.
 * 
 * @param gameId The unique ID of the game.
 */
fun GameDetailDto.toDomain(gameId: String) = GameDetail(
    gameInfo = info.toDomain(gameId),
    cheapestPriceEver = cheapestPriceEver.toDomain(),
    deals = deals.map { it.toDomain() }
)

/**
 * Maps [GameInfoDto] from the data layer to [GameInfo] in the domain layer.
 * 
 * @param gameId The unique ID of the game.
 */
fun GameInfoDto.toDomain(gameId: String) = GameInfo(
    id = gameId,
    name = title,
    thumbnail = thumb
)

/**
 * Maps [CheapestPriceDto] from the data layer to [CheapestPrice] in the domain layer.
 */
fun CheapestPriceDto.toDomain() = CheapestPrice(
    price = price,
    date = date
)

/**
 * Maps [DealSummaryDto] from the data layer to [GameDeal] in the domain layer.
 */
fun DealSummaryDto.toDomain() = GameDeal(
    storeId = storeID,
    dealId = dealID,
    price = price,
    retailPrice = retailPrice,
    savings = savings.toDoubleOrNull() ?: 0.0,
)
