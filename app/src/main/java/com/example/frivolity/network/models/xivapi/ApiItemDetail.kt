package com.example.frivolity.network.models.xivapi

import com.example.frivolity.ui.models.UiItemDetail
import com.google.gson.annotations.SerializedName

data class ApiItemDetail(
    @SerializedName("Name") val name: String,
    @SerializedName("ID") val id: Int,
    @SerializedName("Icon") val iconUrl: String,
    @SerializedName("LevelEquip") val levelToEquip: Int,
    @SerializedName("LevelItem") val iLevel: Int,
    @SerializedName("ClassJobCategory.Name") var jobToEquip: String
)

fun ApiItemDetail.asUiItemDetail() =
    UiItemDetail(
        name, id, iconUrl, levelToEquip, iLevel, jobToEquip
    )