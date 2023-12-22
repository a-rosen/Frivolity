package com.example.frivolity.network.models.universalisapi

data class ApiMarketItemDetail(
    val itemID: Int = 0,
    val worldID: Int = 0,
    val lastUploadTime: Long = 0,
    val listings: List<ApiListingDetail> = listOf(),
    val recentHistory: List<ApiMarketItemSaleData> = listOf(),
    val currentAveragePrice: Float = 0f,
    val currentAveragePriceNQ: Float = 0f,
    val currentAveragePriceHQ: Float = 0f,
    val regularSaleVelocity: Float = 0f,
    val nqSaleVelocity: Float = 0f,
    val hqSaleVelocity: Float = 0f,
    val averagePrice: Float = 0f,
    val averagePriceNQ: Float = 0f,
    val averagePriceHQ: Float = 0f,
    val minPrice: Int = 0,
    val minPriceNQ: Int = 0,
    val minPriceHQ: Int = 0,
    val maxPrice: Int = 0,
    val maxPriceNQ: Int = 0,
    val maxPriceHQ: Int = 0,
    val stackSizeHistogram: Map<String, Int> = mapOf(),
    val stackSizeHistogramNQ: Map<String, Int> = mapOf(),
    val stackSizeHistogramHQ: Map<String, Int> = mapOf(),
    val worldName: String = "TestWorld",
    val listingsCount: Int = 0,
    val recentHistoryCount: Int = 0,
    val unitsForSale: Int = 0,
    val unitsSold: Int  = 0,
)

