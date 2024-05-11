package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiLogicalDc
import com.example.frivolity.network.models.universalisapi.ApiWorld
import com.example.frivolity.network.models.xivapi.ApiItem
import com.example.frivolity.ui.Asynchronous

data class MainScreenState(
    val dataCentersList: Asynchronous<List<ApiDataCenter>>,
    val worldsList: Asynchronous<List<ApiWorld>>,
    val selectedServer: ApiLogicalDc?,
    val selectedWorld: ApiWorld?,
    val searchBoxText: String,
    val searchResults: List<ApiItem>,
) {
    companion object {
        val EMPTY = MainScreenState(
            dataCentersList = Asynchronous.Uninitialized(),
            worldsList = Asynchronous.Uninitialized(),
            selectedServer = null,
            selectedWorld = null,
            searchBoxText = "",
            searchResults = listOf(),
        )
    }
}