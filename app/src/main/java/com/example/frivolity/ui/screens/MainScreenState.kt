package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiItemList
import com.example.frivolity.network.models.universalisapi.ApiWorld

data class MainScreenState(
    val dataCentersList: List<ApiDataCenter>,
    val worldsList: List<ApiWorld>,
    val recentlyUpdatedList: ApiItemList,
    val selectedDC: String,
    val selectedWorld: String
) {
    companion object {
        val EMPTY = MainScreenState(
            dataCentersList = listOf(),
            worldsList = listOf(),
            recentlyUpdatedList = ApiItemList(listOf()),
            selectedDC = "",
            selectedWorld = ""
        )
    }
}