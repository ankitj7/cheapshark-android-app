package com.example.cheapsharkgames.gamedeal.presentation.gamedetail

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cheapsharkgames.R
import com.example.cheapsharkgames.gamedeal.domain.usecase.GetGameDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * ViewModel for the Game Detail screen, following the MVI pattern.
 */
@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val getGameDetailUseCase: GetGameDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(GameDetailState())
    val state: StateFlow<GameDetailState> = _state.asStateFlow()

    init {
        // Retrieve gameId from navigation arguments
        savedStateHandle.get<String>("gameId")?.let { gameId ->
            onIntent(GameDetailIntent.LoadGameDetail(gameId))
        }
    }

    fun onIntent(intent: GameDetailIntent) {
        when (intent) {
            is GameDetailIntent.LoadGameDetail -> fetchGameDetail(intent.gameId)
        }
    }

    private fun fetchGameDetail(gameId: String) {
        getGameDetailUseCase(gameId)
            .onStart {
                _state.value = _state.value.copy(isLoading = true, error = null)
            }
            .onEach { result ->
                // TODO CheapShark - Also load Stores and link them with game deals, and show store name on detail screen
                result.fold(
                    onSuccess = { detail ->
                        _state.value = _state.value.copy(
                            gameDetail = detail,
                            isLoading = false
                        )
                    },
                    onFailure = { exception ->
                        _state.value = _state.value.copy(
                            error = exception.localizedMessage,
                            isLoading = false
                        )
                    }
                )
            }
            .launchIn(viewModelScope)
    }
}
