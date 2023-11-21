package com.example.frivolity.di

import com.example.frivolity.network.UniversalisApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideUniversalisApi() = Retrofit.Builder()
        .baseUrl("https://universalis.app/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UniversalisApi::class.java)
}