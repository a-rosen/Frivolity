package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiLogicalDc
import com.example.frivolity.network.models.universalisapi.ApiWorld
import com.example.frivolity.network.models.xivapi.ApiItem
import com.example.frivolity.ui.Asynchronous
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.immutableListOf

data class MainScreenState(
    val dataCentersList: Asynchronous<ImmutableList<ApiDataCenter>>,
    val worldsList: Asynchronous<ImmutableList<ApiWorld>>,
    val selectedServer: ApiLogicalDc?,
    val selectedWorld: ApiWorld?,
    val searchBoxText: String,
    val searchResults: ImmutableList<ApiItem>,
) {
    companion object {
        val EMPTY = MainScreenState(
            dataCentersList = Asynchronous.Uninitialized(),
            worldsList = Asynchronous.Uninitialized(),
            selectedServer = null,
            selectedWorld = null,
            searchBoxText = "",
            searchResults = immutableListOf(),
        )
    }
}