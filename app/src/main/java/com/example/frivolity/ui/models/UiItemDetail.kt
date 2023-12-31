package com.example.frivolity.ui.models

data class UiItemDetail(
    val name: String,
    val id: Int,
    val iconUrl: String,
    val levelToEquip: Int?,
    val iLevel: Int,
    val jobToEquip: Map<String, Any>?,
    val description: String,
    val type: String?,
)