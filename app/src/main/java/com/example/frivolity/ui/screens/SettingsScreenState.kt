package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.ui.Asynchronous

data class SettingsScreenState(
    val dcListRaw: Asynchronous<String>,
    val dcList: List<ApiDataCenter>
) {
    companion object {
        val EMPTY = SettingsScreenState(
            dcListRaw = Asynchronous.Uninitialized(),
            dcList = listOf()
        )
    }
}