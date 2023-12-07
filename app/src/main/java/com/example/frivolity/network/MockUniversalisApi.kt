package com.example.frivolity.network

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiItemDetail
import com.example.frivolity.network.models.universalisapi.ApiItemList
import com.example.frivolity.network.models.universalisapi.ApiWorld
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

    override suspend fun getWorlds(): List<ApiWorld> {
        return listOf()
    }

    override suspend fun getItemDetails(itemID: Int): ApiItemDetail? {
        return null
    }
}