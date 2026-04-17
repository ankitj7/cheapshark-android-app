package com.example.cheapsharkgames.gamedeal.presentation.gamelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheapsharkgames.core.util.NavigationEvent
import com.example.cheapsharkgames.gamedeal.domain.usecase.GetGamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Game List screen, following the MVI pattern and supporting pagination.
 *
 * @property getGamesUseCase Use case to fetch the list of games.
 */
@HiltViewModel
class GameListViewModel @Inject constructor(
    private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GameListState())

    /**
     * The current UI state of the Game List screen.
     */
    val state: StateFlow<GameListState> = _state.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<NavigationEvent>()
    val navigationEvent: SharedFlow<NavigationEvent> = _navigationEvent.asSharedFlow()

    private var currentPage = 0

    init {
        onIntent(GameListIntent.LoadGames)
    }

    /**
     * Handles user intentions for the Game List screen.
     *
     * @param intent The [GameListIntent] to process.
     */
    fun onIntent(intent: GameListIntent) {
        when (intent) {
            is GameListIntent.LoadGames -> {
                currentPage = 0
                _state.value = GameListState() // Reset state
                getGames()
            }
            is GameListIntent.LoadNextPage -> {
                if (!_state.value.isLoading && !_state.value.isNextPageLoading && !_state.value.endOfPagination) {
                    currentPage++
                    getGames()
                }
            }
            is GameListIntent.GameClicked -> {
                viewModelScope.launch {
                    _navigationEvent.emit(NavigationEvent.NavigateToDetail(intent.gameId))
                }
            }
        }
    }

    private fun getGames() {
        val isFirstPage = currentPage == 0
        getGamesUseCase(currentPage)
            .onStart {
                if (isFirstPage) {
                    _state.value = _state.value.copy(isLoading = true, error = null)
                } else {
                    _state.value = _state.value.copy(isNextPageLoading = true)
                }
            }
            .onEach { result ->
                result.fold(
                    onSuccess = { newGames ->
                        val currentGames = _state.value.games
                        _state.value = _state.value.copy(
                            games = if (isFirstPage) newGames else (currentGames + newGames).distinctBy { it.id },
                            isLoading = false,
                            isNextPageLoading = false,
                            endOfPagination = newGames.isEmpty()
                        )
                    },
                    onFailure = { exception ->
                        _state.value = _state.value.copy(
                            error = exception.localizedMessage,
                            isLoading = false,
                            isNextPageLoading = false
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }
}
