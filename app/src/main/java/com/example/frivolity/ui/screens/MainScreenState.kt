package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.ApiDataCenter
import com.example.frivolity.network.models.ApiItemList

data class MainScreenState(
    val dataCentersList: List<ApiDataCenter>,
    val recentlyUpdatedList: ApiItemList
) {
    companion object {
        val EMPTY = MainScreenState(
            dataCentersList = listOf(),
            recentlyUpdatedList = ApiItemList(listOf()),
        )
    }
}