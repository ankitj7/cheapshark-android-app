package com.example.cheapsharkgames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cheapsharkgames.gamedeal.presentation.gamelist.GameListScreen
import com.example.cheapsharkgames.ui.theme.CheapSharkGamesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheapSharkGamesTheme {
                GameListScreen()
            }
        }
    }
}
