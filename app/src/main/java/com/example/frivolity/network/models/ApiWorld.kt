package com.example.frivolity.network.models

import com.google.gson.annotations.SerializedName

data class ApiWorld (
    @SerializedName("ID")
    val id: Int,
    @SerializedName("Icon")
    val icon: String,
    @SerializedName("Name")
    val name: String?,
    @SerializedName("Url")
    val url: String
)