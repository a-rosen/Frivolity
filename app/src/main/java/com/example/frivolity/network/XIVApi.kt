package com.example.frivolity.network

import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemList
import com.example.frivolity.network.models.xivapi.ApiWorldList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface XIVApi {
    @GET("World")
    suspend fun getWorlds(): ApiWorldList

    @GET("item/{id}")
    suspend fun getItemById(
        @Path("id") id: Int
    ): ApiItemDetail

    @GET("search?filters=IsUntradable=0&string={string}")
    suspend fun itemSearchByString(
        @Query("string") string: String
    ): ApiItemList
}