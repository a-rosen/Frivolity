package com.example.frivolity.network.models.universalisapi

import com.example.frivolity.repository.models.ItemPricesData

data class ApiPrices(
    val quantity: Int,
    val total: Int,
    val pricePerUnit: Int,
    val worldName: String
) {
    fun asItemPricesData() {
        ItemPricesData(
            quantity, total, pricePerUnit, worldName
        )
    }
}

