package com.example.cheapsharkgames.gamedeal.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DealDto(
    @SerialName("internalName") val internalName: String,
    @SerialName("title") val title: String,
    @SerialName("metacriticLink") val metacriticLink: String?,
    @SerialName("dealID") val dealID: String,
    @SerialName("storeID") val storeID: String,
    @SerialName("gameID") val gameID: String,
    @SerialName("salePrice") val salePrice: String,
    @SerialName("normalPrice") val normalPrice: String,
    @SerialName("isOnSale") val isOnSale: String,
    @SerialName("savings") val savings: String,
    @SerialName("metacriticScore") val metacriticScore: String,
    @SerialName("releaseDate") val releaseDate: Long,
    @SerialName("lastChange") val lastChange: Long,
    @SerialName("dealRating") val dealRating: String,
    @SerialName("thumb") val thumb: String
)
