package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiDataCenter

data class SettingsScreenState(
    val dcList: List<ApiDataCenter>
) {
    companion object {
        val EMPTY = SettingsScreenState(
            dcList = listOf()
        )
    }
}