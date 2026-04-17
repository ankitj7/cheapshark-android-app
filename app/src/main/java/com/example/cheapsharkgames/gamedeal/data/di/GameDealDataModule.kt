package com.example.cheapsharkgames.gamedeal.data.di

import com.example.cheapsharkgames.gamedeal.data.repository.GameRepositoryImpl
import com.example.cheapsharkgames.gamedeal.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for providing dependencies related to the Game Deal feature data layer.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class GameDealDataModule {

    @Binds
    @Singleton
    abstract fun bindGameRepository(
        gameRepositoryImpl: GameRepositoryImpl
    ): GameRepository
}
