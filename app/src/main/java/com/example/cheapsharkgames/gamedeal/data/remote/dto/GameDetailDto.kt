package com.example.cheapsharkgames.gamedeal.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDetailDto(
    @SerialName("info") val info: GameInfoDto,
    @SerialName("cheapestPriceEver") val cheapestPriceEver: CheapestPriceDto,
    @SerialName("deals") val deals: List<DealSummaryDto>
)

@Serializable
data class GameInfoDto(
    @SerialName("title") val title: String,
    @SerialName("thumb") val thumb: String
)

@Serializable
data class CheapestPriceDto(
    @SerialName("price") val price: String,
    @SerialName("date") val date: Long
)

@Serializable
data class DealSummaryDto(
    @SerialName("storeID") val storeID: String,
    @SerialName("dealID") val dealID: String,
    @SerialName("price") val price: String,
    @SerialName("retailPrice") val retailPrice: String,
    @SerialName("savings") val savings: String
)
