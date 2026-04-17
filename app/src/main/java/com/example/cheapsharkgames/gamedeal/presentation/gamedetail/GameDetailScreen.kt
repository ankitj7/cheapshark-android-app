package com.example.cheapsharkgames.gamedeal.presentation.gamedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.cheapsharkgames.R
import com.example.cheapsharkgames.core.util.FormattingUtils.formatPrice
import com.example.cheapsharkgames.core.util.FormattingUtils.formatSavings
import com.example.cheapsharkgames.gamedeal.domain.model.CheapestPrice
import com.example.cheapsharkgames.gamedeal.domain.model.GameDeal
import com.example.cheapsharkgames.gamedeal.domain.model.GameDetail
import com.example.cheapsharkgames.gamedeal.domain.model.GameInfo
import com.example.cheapsharkgames.ui.theme.CheapSharkGamesTheme
import com.example.cheapsharkgames.ui.theme.Dimensions

/**
 * Screen that displays detailed information for a specific game.
 *
 * @param viewModel The [GameDetailViewModel] that provides the screen state.
 * @param onBackClick Callback triggered when the back button is clicked.
 */
@Composable
fun GameDetailScreen(
    viewModel: GameDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    GameDetailContent(
        state = state,
        onBackClick = onBackClick
    )
}

@Composable
fun GameDetailContent(
    state: GameDetailState,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            state.gameDetail?.let { detail ->
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        GameHeader(
                            title = detail.gameInfo.name,
                            thumbnail = detail.gameInfo.thumbnail
                        )
                    }

                    item {
                        detail.cheapestPriceEver?.let {
                            CheapestPriceEver(price = it.price)
                        }
                    }

                    item {
                        Text(
                            text = stringResource(R.string.deals_header),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(Dimensions.PaddingMedium),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    items(detail.deals) { deal ->
                        DealItem(deal = deal)
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = Dimensions.PaddingMedium),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
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

@Composable
fun GameHeader(title: String, thumbnail: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = thumbnail,
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(Dimensions.PaddingMedium),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CheapestPriceEver(price: String) {
    Text(
        text = stringResource(R.string.cheapest_ever, formatPrice(price)),
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(horizontal = Dimensions.PaddingMedium),
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun DealItem(deal: GameDeal) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.PaddingMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(R.string.store_label, deal.storeId), style = MaterialTheme.typography.bodyMedium)
        }
        
        if (deal.savings > 0) {
            Text(
                text = "-${formatSavings(deal.savings)}",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50) // Green
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = formatPrice(deal.retailPrice),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary,
                textDecoration = TextDecoration.LineThrough
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = formatPrice(deal.price),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameDetailScreenPreview() {
    CheapSharkGamesTheme {
        GameDetailContent(
            state = GameDetailState(
                gameDetail = GameDetail(
                    gameInfo = GameInfo(
                        id = "1",
                        name = "Sample Game Title",
                        thumbnail = "https://cdn.cloudflare.steamstatic.com/steam/apps/6920/capsule_sm_120.jpg"
                    ),
                    cheapestPriceEver = CheapestPrice(
                        price = "4.99",
                        date = 1625097600L
                    ),
                    deals = listOf(
                        GameDeal(
                            storeId = "1",
                            dealId = "deal1",
                            price = "9.99",
                            retailPrice = "29.99",
                            savings = 66.67
                        ),
                        GameDeal(
                            storeId = "2",
                            dealId = "deal2",
                            price = "14.99",
                            retailPrice = "14.99",
                            savings = 0.0
                        )
                    )
                )
            ),
            onBackClick = {}
        )
    }
}
