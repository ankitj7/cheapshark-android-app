package com.example.cheapsharkgames

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cheapsharkgames.core.util.NavigationConstants.ARG_GAME_ID
import com.example.cheapsharkgames.core.util.NavigationConstants.ROUTE_GAME_DETAIL
import com.example.cheapsharkgames.core.util.NavigationConstants.ROUTE_GAME_LIST
import com.example.cheapsharkgames.core.util.NavigationConstants.gameDetailPath
import com.example.cheapsharkgames.core.util.NavigationEvent
import com.example.cheapsharkgames.gamedeal.presentation.gamedetail.GameDetailScreen
import com.example.cheapsharkgames.gamedeal.presentation.gamelist.GameListScreen
import com.example.cheapsharkgames.gamedeal.presentation.gamelist.GameListViewModel
import com.example.cheapsharkgames.ui.theme.CheapSharkGamesTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheapSharkGamesTheme {
                CheapSharkApp()
            }
        }
    }
}

@Composable
fun CheapSharkApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ROUTE_GAME_LIST
    ) {
        composable(ROUTE_GAME_LIST) {
            val viewModel: GameListViewModel = hiltViewModel()

            // Observe navigation events from ViewModel
            LaunchedEffect(viewModel.navigationEvent) {
                viewModel.navigationEvent.collectLatest { event ->
                    when (event) {
                        is NavigationEvent.NavigateToDetail -> {
                            navController.navigate(gameDetailPath(event.gameId))
                        }
                        else -> Unit
                    }
                }
            }

            GameListScreen(
                viewModel = viewModel
            )
        }
        composable(
            route = ROUTE_GAME_DETAIL,
            arguments = listOf(
                navArgument(ARG_GAME_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            GameDetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
