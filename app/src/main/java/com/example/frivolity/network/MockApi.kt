package com.example.frivolity.network

import com.example.frivolity.network.models.ApiDataCenter
import com.example.frivolity.network.models.ApiItemList

class MockApi: UniversalisApi {
    override suspend fun getDataCenters(): List<ApiDataCenter> {
        return listOf()
    }

    override suspend fun getRecentlyUpdated(): ApiItemList {
        return ApiItemList(items = listOf())
    }
}