package com.example.frivolity.network.models.universalisapi

data class ApiItemSaleData(
    val hq: Boolean,
    val pricePerUnit: Long,
    val quantity: Int,
    val timestamp: Long,
    val onMannequin: Boolean,
    val buyerName: String,
    val total: Long
)