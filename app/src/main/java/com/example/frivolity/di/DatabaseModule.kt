package com.example.frivolity.di

import android.content.Context
import androidx.room.Room
import com.example.frivolity.database.FrivolityDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FrivolityDatabase {
        return Room
            .databaseBuilder(
                context,
                FrivolityDatabase::class.java,
                "frivolity-database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun serverInfoDao(
        database: FrivolityDatabase
    ) = database.serverInfoDao()
}