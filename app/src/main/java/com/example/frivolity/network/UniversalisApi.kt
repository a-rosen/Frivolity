package com.example.frivolity.network

import com.example.frivolity.network.models.ApiDataCenter
import com.example.frivolity.network.models.ApiItemList
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
}