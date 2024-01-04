package com.example.frivolity.network.models.universalisapi

data class ApiPrices(
    val quantity: Int,
    val total: Int,
    val pricePerUnit: Int,
    val worldName: String
)