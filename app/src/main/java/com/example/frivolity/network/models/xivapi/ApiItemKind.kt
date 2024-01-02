package com.example.frivolity.network.models.xivapi

import com.google.gson.annotations.SerializedName

data class ApiItemKind(
    @SerializedName("Name") val type: String,
)