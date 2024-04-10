package com.example.frivolity.di

import com.example.frivolity.network.UniversalisApi
import com.example.frivolity.network.XIVApi
import com.google.gson.Gson
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
    fun provideOkHttpClient(logInterceptor: Interceptor) = OkHttpClient
        .Builder()
        .addInterceptor(logInterceptor)
        .build()

    @Provides
    internal fun provideHttpLoggingInterceptor(): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    @Provides
    @Singleton
    internal fun provideGsonConvFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideUniversalisApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ) = Retrofit.Builder()
        .baseUrl("https://universalis.app/api/v2/")
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
        .create(UniversalisApi::class.java)

    @Provides
    @Singleton
    fun provideXIVApi(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ) = Retrofit.Builder()
        .baseUrl("https://xivapi.com/")
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
        .create(XIVApi::class.java)
}