package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiMarketItemDetail
import com.example.frivolity.network.models.universalisapi.ApiPrices
import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemKind

data class ItemDetailScreenState(
    val marketItemDetail: ApiMarketItemDetail,
    val itemDetail: ApiItemDetail,
    val regionToSearch: String,
    val cheapestPrice: ApiPrices?
) {
    companion object {
        val EMPTY = ItemDetailScreenState(
            marketItemDetail = ApiMarketItemDetail(
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
            ),
            itemDetail = ApiItemDetail(
                "", 0, "", 0, 0, mapOf(Pair("Name", "JOB JOB JOB JOB")), "", ApiItemKind("")
            ),
            regionToSearch = "North-America",
            cheapestPrice = ApiPrices(0, 0, "Faerie")
        )
    }
}

