package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.ApiDataCenter

data class MainScreenState(
    val dataCentersList: List<ApiDataCenter>
) {
    companion object {
        val EMPTY = MainScreenState(
            dataCentersList = listOf(),
        )
    }
}