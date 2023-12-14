package com.example.frivolity.network.models.xivapi

import com.example.frivolity.repository.models.ItemDetailDataRecord
import com.google.gson.annotations.SerializedName

data class ApiItemDetail(
    @SerializedName("Name") val name: String,
    @SerializedName("ID") val id: Int,
    @SerializedName("Icon") val iconUrl: String,
    @SerializedName("LevelEquip") val levelToEquip: Int,
    @SerializedName("LevelItem") val iLevel: Int,
)

fun ApiItemDetail.asItemDetailDataRecord() =
    ItemDetailDataRecord(
        name, iconUrl, id, iLevel
    )