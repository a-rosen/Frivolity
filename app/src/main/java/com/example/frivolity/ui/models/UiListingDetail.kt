package com.example.frivolity.ui.models

data class UiListingDetail(
    val pricePerUnit: Int,
    val quantity: Int,
    val hq: Boolean,
    val retainerName: String,
    val retainerCity: Int,
    val total: Int,
)