package com.example.frivolity.network

import com.example.frivolity.network.models.ApiDataCenter
import retrofit2.http.GET

interface UniversalisApi {
    @GET("data-centers")
    suspend fun getDataCenters() : List<ApiDataCenter>
}