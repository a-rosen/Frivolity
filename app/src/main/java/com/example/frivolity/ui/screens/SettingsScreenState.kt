package com.example.frivolity.ui.screens

import com.example.frivolity.ui.Asynchronous

data class SettingsScreenState(
    val dcListRaw: Asynchronous<String>,
) {
    companion object {
        val EMPTY = SettingsScreenState(
            dcListRaw = Asynchronous.Uninitialized()
        )
    }
}