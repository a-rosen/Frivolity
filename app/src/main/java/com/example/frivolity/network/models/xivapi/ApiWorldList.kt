package com.example.frivolity.network.models.xivapi

import com.google.gson.annotations.SerializedName

data class ApiWorldList(
    @SerializedName("Results")
    val results: List<ApiWorld>
)