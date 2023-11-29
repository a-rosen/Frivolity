package com.example.frivolity.network

import com.example.frivolity.network.models.ApiWorldList
import retrofit2.http.GET

interface XIVApi {
    @GET("World")
    suspend fun getWorlds(): ApiWorldList
}