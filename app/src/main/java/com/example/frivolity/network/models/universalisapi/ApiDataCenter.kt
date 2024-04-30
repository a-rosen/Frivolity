package com.example.frivolity.network.models.universalisapi

import com.google.gson.annotations.SerializedName

data class ApiDataCenter(
    val name: String,
    val region: String,
    @SerializedName("worlds") val worldIds: List<Int>
)