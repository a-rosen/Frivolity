package com.example.frivolity.network

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiMarketItemDetail
import com.example.frivolity.network.models.universalisapi.ApiPricesList
import com.example.frivolity.network.models.universalisapi.ApiWorld
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UniversalisApi {
    @GET("data-centers")
    suspend fun getDataCenters(): List<ApiDataCenter>

    @GET("data-centers")
    suspend fun getDcsRaw(): ResponseBody

    @GET("worlds")
    suspend fun getWorlds(): List<ApiWorld>

    @GET("worlds")
    suspend fun getWorldsRaw(): ResponseBody

    @GET("{world}/{id}")
    suspend fun getItemDetails(
        @Path("world") world: String,
        @Path("id") id: Int
    ): ApiMarketItemDetail

    @GET("{region}/{id}")
    suspend fun getItemPrices(
        @Path("region") region: String,
        @Path("id") id: Int,
        @Query("fields") fields: String
    ): ApiPricesList
}