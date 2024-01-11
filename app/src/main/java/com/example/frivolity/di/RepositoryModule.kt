package com.example.frivolity.di

import com.example.frivolity.repository.FrivolityRepository
import com.example.frivolity.repository.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideFrivolityRepository(
        networkRepository: NetworkRepository
    ): FrivolityRepository
}