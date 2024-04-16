package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiWorld

data class SettingsScreenState(
    val dcList: List<ApiDataCenter>,
    val worldsList: List<ApiWorld>
) {
    companion object {
        val EMPTY = SettingsScreenState(
            dcList = listOf(),
            worldsList = listOf()
        )
    }
}