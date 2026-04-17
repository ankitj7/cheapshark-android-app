package com.example.cheapsharkgames.core.util

/**
 * Represents navigation events that can be emitted by ViewModels.
 */
sealed class NavigationEvent {
    /**
     * Navigates to the Game Detail screen.
     * 
     * @property gameId The ID of the game to show details for.
     */
    data class NavigateToDetail(val gameId: String) : NavigationEvent()

    /**
     * Navigates back to the previous screen.
     */
    object NavigateBack : NavigationEvent()
}
