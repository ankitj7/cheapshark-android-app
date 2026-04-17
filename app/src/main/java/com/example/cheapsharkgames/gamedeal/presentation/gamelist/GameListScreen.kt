package com.example.cheapsharkgames.gamedeal.presentation.gamelist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.cheapsharkgames.R
import com.example.cheapsharkgames.gamedeal.domain.model.GameInfo
import com.example.cheapsharkgames.ui.theme.CheapSharkGamesTheme
import com.example.cheapsharkgames.ui.theme.Dimensions

/**
 * Screen that displays a list of game deals using the MVI pattern.
 *
 * @param viewModel The [GameListViewModel] that provides the screen state and handles intents.
 */
@Composable
fun GameListScreen(
    viewModel: GameListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.PaddingMedium),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.game_list_title),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.games) { game ->
                    GameItem(
                        game = game,
                        onClick = {
                            viewModel.onIntent(GameListIntent.GameClicked(game.id))
                        }
                    )
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            state.error?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(Dimensions.PaddingMedium)
                )
            }
        }
    }
}

/**
 * A single game item within the list.
 *
 * @param game The [GameInfo] to display.
 * @param onClick Callback triggered when this item is clicked.
 */
@Composable
fun GameItem(
    game: GameInfo,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = Dimensions.PaddingMedium, vertical = Dimensions.PaddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(Dimensions.PaddingXSmall),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = Dimensions.ElevationSmall),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onSurface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            border = BorderStroke(Dimensions.BorderThin, MaterialTheme.colorScheme.onSurface)
        ) {
            Box(
                modifier = Modifier
                    .width(Dimensions.GameThumbnailWidth)
                    .height(Dimensions.GameThumbnailHeight),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = game.thumbnail,
                    contentDescription = game.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        Spacer(modifier = Modifier.width(Dimensions.SpacingMedium))

        Text(
            text = game.name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemPreview() {
    CheapSharkGamesTheme {
        GameItem(
            game = GameInfo(
                id = "1",
                name = "Sample Game Title",
                thumbnail = "https://cdn.cloudflare.steamstatic.com/steam/apps/6920/capsule_sm_120.jpg?t=1593593178"
            ),
            onClick = {}
        )
    }
}
