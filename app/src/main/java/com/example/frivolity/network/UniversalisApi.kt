package com.example.frivolity.network

import com.example.frivolity.network.models.ApiDataCenter
import com.example.frivolity.network.models.ApiItemList
import retrofit2.http.GET

interface UniversalisApi {
    @GET("data-centers")
    suspend fun getDataCenters() : List<ApiDataCenter>

    @GET("extra/stats/most-recently-updated?world=Faerie&dcName=North-America&entries=2")
    suspend fun getRecentlyUpdated() : ApiItemList
}