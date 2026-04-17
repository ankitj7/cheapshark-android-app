package com.example.cheapsharkgames.gamedeal.domain.model

/**
 * Detailed information for a specific game, including history and current deals.
 *
 * @property gameInfo Basic information about the game (title and thumbnail).
 * @property cheapestPriceEver The lowest price this game has ever been recorded at.
 * @property deals A list of all [GameDeal]s currently available across different stores.
 */
data class GameDetail(
    val gameInfo: GameInfo,
    val cheapestPriceEver: CheapestPrice?,
    val deals: List<GameDeal>
)

/**
 * Historical record of a game's lowest price.
 *
 * @property price The lowest price value recorded.
 * @property date The date when this price was recorded, as a Unix timestamp.
 */
data class CheapestPrice(
    val price: String,
    val date: Long
)

/**
 * Information about a specific deal available for a game.
 *
 * @property storeId The ID of the store where this deal is located.
 * @property dealId The unique ID of the specific deal.
 * @property price The current price of the game at this store.
 * @property retailPrice The standard retail price at this store.
 * @property savings The percentage savings for this specific deal.
 */
data class GameDeal(
    val storeId: String,
    val dealId: String,
    val price: String,
    val retailPrice: String,
    val savings: String
)
