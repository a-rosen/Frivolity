package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiLogicalDc
import com.example.frivolity.network.models.universalisapi.ApiMarketItemDetail
import com.example.frivolity.network.models.universalisapi.ApiPrices
import com.example.frivolity.network.models.universalisapi.ApiWorld
import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemKind
import com.example.frivolity.ui.Asynchronous
import com.example.frivolity.ui.models.SortMethods

data class ItemDetailScreenState(
    val worldId: Int?,
    val currentLogicalDc: Asynchronous<ApiLogicalDc>,
    val currentDcWorldsList: Asynchronous<List<ApiWorld>>,
    val marketItemDetail: ApiMarketItemDetail,
    val itemDetail: ApiItemDetail?,
    val cheapestTotalPriceRegion: Asynchronous<ApiPrices>,
    val cheapestUnitPriceRegion: Asynchronous<ApiPrices>,
    val cheapestTotalPriceDc: Asynchronous<ApiPrices>,
    val cheapestUnitPriceDc: Asynchronous<ApiPrices>,
    val sortMethod: SortMethods,
    val showHqOnly: Boolean,
    val shouldShowStatsRow: Boolean,
    val shouldShowWorldList: Boolean,

    ) {
    companion object {
        val EMPTY = ItemDetailScreenState(
            marketItemDetail = ApiMarketItemDetail(
                itemID = 1,
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
                "",
                1,
                "",
                0,
                0,
                mapOf(Pair("Name", "JOB JOB JOB JOB")),
                "",
                ApiItemKind("")
            ),
            cheapestTotalPriceRegion = Asynchronous.Uninitialized(),
            cheapestUnitPriceRegion = Asynchronous.Uninitialized(),
            cheapestTotalPriceDc = Asynchronous.Uninitialized(),
            cheapestUnitPriceDc = Asynchronous.Uninitialized(),
            sortMethod = SortMethods.TOTAL,
            showHqOnly = false,
            shouldShowStatsRow = false,
            currentLogicalDc = Asynchronous.Uninitialized(),
            currentDcWorldsList = Asynchronous.Uninitialized(),
            worldId = 0,
            shouldShowWorldList = false
        )
    }
}

