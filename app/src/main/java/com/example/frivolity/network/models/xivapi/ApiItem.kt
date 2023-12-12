package com.example.frivolity.network.models.xivapi

import com.google.gson.annotations.SerializedName

data class ApiItem(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Url")
    val url: String,
)