package com.example.frivolity.ui.screens

import com.example.frivolity.network.models.universalisapi.ApiItemDetail

data class ItemDetailScreenState (
    val item: ApiItemDetail?
) {
    companion object {
        val EMPTY = ItemDetailScreenState(
            item = null
        )
    }
}