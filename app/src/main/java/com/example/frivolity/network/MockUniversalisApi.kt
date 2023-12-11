package com.example.frivolity.network

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiItemDetail
import com.example.frivolity.network.models.universalisapi.ApiItemList
import com.example.frivolity.network.models.universalisapi.ApiWorld
import retrofit2.http.Path
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

    override suspend fun getItemDetails(
        @Path(value = "world") world: String,
        @Path(value = "id") id: Int
    ): ApiItemDetail {
        return ApiItemDetail(
            itemID = 0,
            worldID = 0,
            lastUploadTime = 0,
            listings = listOf(),
            recentHistory = listOf(),
            currentAveragePrice = 0f,
            currentAveragePriceNQ = 0f,
            currentAveragePriceHQ = 0f,
            regularSaleVelocity = 0f,
            nqSaleVelocity = 0f,
            hqSaleVelocity = 0f,
            averagePrice = 0f,
            averagePriceNQ = 0f,
            averagePriceHQ = 0f,
            minPrice = 0,
            minPriceNQ = 0,
            minPriceHQ = 0,
            maxPrice = 0,
            maxPriceNQ = 0,
            maxPriceHQ = 0,
            stackSizeHistogram = mapOf(),
            stackSizeHistogramNQ = mapOf(),
            stackSizeHistogramHQ = mapOf(),
            worldName = "",
            listingsCount = 0,
            recentHistoryCount = 0,
            unitsForSale = 0,
            unitsSold = 0
        )
    }
}