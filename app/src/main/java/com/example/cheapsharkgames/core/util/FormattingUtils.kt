package com.example.cheapsharkgames.core.util

import java.util.Locale

/**
 * Utility functions for formatting data for display in the UI.
 */
object FormattingUtils {
    /**
     * Formats a numeric savings value as a percentage string.
     * 
     * @param savings The numeric savings value (e.g., 80.0).
     * @return A formatted string (e.g., "80%").
     */
    fun formatSavings(savings: Double): String {
        return "${savings.toInt()}%"
    }

    /**
     * Formats a price value with a currency symbol.
     * 
     * @param price The price value as a string.
     * @return A formatted currency string (e.g., "$19.99").
     */
    fun formatPrice(price: String): String {
        return "\$$price"
    }
}
