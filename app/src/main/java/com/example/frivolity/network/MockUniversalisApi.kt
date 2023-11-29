package com.example.frivolity.network

import com.example.frivolity.network.models.ApiDataCenter
import com.example.frivolity.network.models.ApiItemList
import retrofit2.http.Query

class MockUniversalisApi: UniversalisApi {
    override suspend fun getDataCenters(): List<ApiDataCenter> {
        return listOf()
    }

    override suspend fun getRecentlyUpdated(
        @Query(value = "world") world: String,
        @Query(value = "dcName") dcName: String,
        @Query(value = "entries") entries: Int
    ): ApiItemList {
        return ApiItemList(items = listOf())
    }
}