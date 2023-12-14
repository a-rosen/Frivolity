package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiMarketItemList
import com.example.frivolity.network.models.universalisapi.ApiWorld
import com.example.frivolity.network.models.xivapi.ApiItem
import com.example.frivolity.repository.models.ItemDetailDataRecord

data class MainScreenState(
    val dataCentersList: List<ApiDataCenter>,
    val worldsList: List<ApiWorld>,
    val recentlyUpdatedList: ApiMarketItemList,
    val selectedDC: ApiDataCenter?,
    val selectedWorld: ApiWorld?,
    val searchBoxText: String,
    val searchResults: List<ApiItem>,
    val searchResultsDetail: List<ItemDetailDataRecord>
) {
    companion object {
        val EMPTY = MainScreenState(
            dataCentersList = listOf(),
            worldsList = listOf(),
            recentlyUpdatedList = ApiMarketItemList(listOf()),
            selectedDC = null,
            selectedWorld = null,
            searchBoxText = "",
            searchResults = listOf(),
            searchResultsDetail = listOf()
        )
    }
}