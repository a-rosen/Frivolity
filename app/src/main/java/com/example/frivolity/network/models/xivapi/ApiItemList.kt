package com.example.frivolity.network.models.xivapi

import com.google.gson.annotations.SerializedName

data class ApiItemList(
    @SerializedName("Results")
    val results: List<ApiItem>
)