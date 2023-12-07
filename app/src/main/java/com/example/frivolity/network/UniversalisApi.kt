package com.example.frivolity.network

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiItemDetail
import com.example.frivolity.network.models.universalisapi.ApiItemList
import com.example.frivolity.network.models.universalisapi.ApiWorld
import retrofit2.http.GET
import retrofit2.http.Query

interface UniversalisApi {
    @GET("data-centers")
    suspend fun getDataCenters(): List<ApiDataCenter>

    @GET("extra/stats/most-recently-updated")
    suspend fun getRecentlyUpdated(
        @Query("world") world: String,
        @Query("dcName") dcName: String,
        @Query("entries") entries: Int
    ): ApiItemList

    @GET("worlds")
    suspend fun getWorlds(): List<ApiWorld>

    @GET("Faerie/39940")
    suspend fun getItemDetails(): ApiItemDetail
}