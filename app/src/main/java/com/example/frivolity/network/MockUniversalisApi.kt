package com.example.frivolity.network

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiMarketItemDetail
import com.example.frivolity.network.models.universalisapi.ApiPricesList
import com.example.frivolity.network.models.universalisapi.ApiWorld
import okhttp3.ResponseBody
import retrofit2.http.Path

class MockUniversalisApi: UniversalisApi {
    override suspend fun getDataCenters(): List<ApiDataCenter> {
        return listOf()
    }

    override suspend fun getDcsRaw(): ResponseBody {
        TODO("Not yet implemented")
    }

    override suspend fun getWorlds(): List<ApiWorld> {
        return listOf()
    }

    override suspend fun getWorldsRaw(): ResponseBody {
        TODO("Not yet implemented")
    }

    override suspend fun getItemDetails(
        @Path(value = "world") world: String,
        @Path(value = "id") id: Int
    ): ApiMarketItemDetail {
        return ApiMarketItemDetail(
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

    override suspend fun getItemPrices(region: String, id: Int, fields: String): ApiPricesList {
        TODO("Not yet implemented")
    }
}