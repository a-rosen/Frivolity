package com.example.frivolity.network.models.universalisapi

data class ApiMarketItemSaleData(
    val hq: Boolean,
    val pricePerUnit: Int,
    val quantity: Int,
    val timestamp: Long,
    val onMannequin: Boolean,
    val buyerName: String,
    val total: Long
)