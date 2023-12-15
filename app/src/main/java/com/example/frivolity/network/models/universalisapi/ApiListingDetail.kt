package com.example.frivolity.network.models.universalisapi

import com.example.frivolity.ui.models.UiListingDetail

data class ApiListingDetail(
    val lastReviewTime: Long,
    val pricePerUnit: Int,
    val quantity: Int,
    val stainID: Int,
    val creatorName: String,
    val creatorID: String,
    val hq: Boolean,
    val isCrafted: Boolean,
    val listingID: String,
    val materia: List<Map<String, Int>>,
    val onMannequin: Boolean,
    val retainerCity: Int,
    val retainerID: String,
    val retainerName: String,
    val sellerID: String,
    val total: Int
)

fun ApiListingDetail.asUiListingDetail() = UiListingDetail(
    pricePerUnit, quantity, hq, retainerName, retainerCity, total
)
