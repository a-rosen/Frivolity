package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiMarketItemDetail
import com.example.frivolity.network.models.universalisapi.ApiPrices
import com.example.frivolity.network.models.universalisapi.ApiWorld
import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemKind
import com.example.frivolity.ui.models.SortMethods

data class ItemDetailScreenState(
    val dcList: List<ApiDataCenter>,
    val worldList: List<ApiWorld>,
    val worldId: Int?,
    val currentDc: ApiDataCenter? = ApiDataCenter("Loading...", "", listOf()),
    val marketItemDetail: ApiMarketItemDetail,
    val itemDetail: ApiItemDetail,
    val regionToSearch: String,
    val cheapestTotalPrice: ApiPrices?,
    val cheapestUnitPrice: ApiPrices?,
    val sortMethod: SortMethods,
    val showHqOnly: Boolean,
    val shouldShowDropdown: Boolean,

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
            cheapestTotalPrice = ApiPrices(0, 0, 0, "Faerie"),
            cheapestUnitPrice = ApiPrices(0, 0, 0, "Faerie"),
            sortMethod = SortMethods.TOTAL,
            showHqOnly = false,
            shouldShowDropdown = false,
            dcList = listOf(),
            worldList = listOf(),
            currentDc = null,
            worldId = 0,
        )
    }
}

