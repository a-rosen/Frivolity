package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiLogicalDc

data class SettingsScreenState(
    val logicalDcsList: List<ApiLogicalDc>,
    val dcHasBeenSelected: Boolean,
    val selectedDcName: String,
    val selectedWorldName: String
) {
    companion object {
        val EMPTY = SettingsScreenState(
            logicalDcsList = listOf(),
            dcHasBeenSelected = false,
            selectedDcName = "",
            selectedWorldName = ""
        )
    }
}