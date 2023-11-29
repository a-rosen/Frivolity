package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.ApiDataCenter
import com.example.frivolity.network.models.ApiItemList
import com.example.frivolity.network.models.ApiPagination
import com.example.frivolity.network.models.ApiWorldList

data class MainScreenState(
    val dataCentersList: List<ApiDataCenter>,
    val worldsList: ApiWorldList,
    val recentlyUpdatedList: ApiItemList,
    val selectedDC: String,
    val selectedWorld: String
) {
    companion object {
        val EMPTY = MainScreenState(
            dataCentersList = listOf(),
            worldsList = ApiWorldList(
                ApiPagination(
                    0, 0, 0, 0, 0, 0, 0
                ), listOf()
            ),
            recentlyUpdatedList = ApiItemList(listOf()),
            selectedDC = "",
            selectedWorld = ""
        )
    }
}