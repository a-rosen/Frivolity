package com.example.frivolity.di

import com.example.frivolity.network.UniversalisApi
import com.example.frivolity.network.XIVApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideClientInterceptor() = OkHttpClient.Builder()
        .addInterceptor(provideHttpLoggingInterceptor())
        .build()

    @Provides
    internal fun provideHttpLoggingInterceptor(): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }
    @Provides
    @Singleton
    fun provideUniversalisApi() = Retrofit.Builder()
        .baseUrl("https://universalis.app/api/v2/")
        .client(provideClientInterceptor())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UniversalisApi::class.java)

    @Provides
    @Singleton
    fun provideXIVApi() = Retrofit.Builder()
        .baseUrl("https://xivapi.com/")
        .client(provideClientInterceptor())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(XIVApi::class.java)
}