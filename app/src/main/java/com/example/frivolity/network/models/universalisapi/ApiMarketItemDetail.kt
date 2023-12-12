package com.example.frivolity.network.models.universalisapi

data class ApiMarketItemDetail(
    val itemID: Int,
    val worldID: Int,
    val lastUploadTime: Long,
    val listings: List<ApiListingDetail>,
    val recentHistory: List<ApiMarketItemSaleData>,
    val currentAveragePrice: Float,
    val currentAveragePriceNQ: Float,
    val currentAveragePriceHQ: Float,
    val regularSaleVelocity: Float,
    val nqSaleVelocity: Float,
    val hqSaleVelocity: Float,
    val averagePrice: Float,
    val averagePriceNQ: Float,
    val averagePriceHQ: Float,
    val minPrice: Int,
    val minPriceNQ: Int,
    val minPriceHQ: Int,
    val maxPrice: Int,
    val maxPriceNQ: Int,
    val maxPriceHQ: Int,
    val stackSizeHistogram: Map<String, Int>,
    val stackSizeHistogramNQ: Map<String, Int>,
    val stackSizeHistogramHQ: Map<String, Int>,
    val worldName: String,
    val listingsCount: Int,
    val recentHistoryCount: Int,
    val unitsForSale: Int,
    val unitsSold: Int
)