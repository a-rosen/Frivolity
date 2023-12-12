package com.example.frivolity.network.models.xivapi

import com.google.gson.annotations.SerializedName

data class ApiItemDetail(
    @SerializedName("Name") val name: String,
    @SerializedName("ID") val id: Int
)