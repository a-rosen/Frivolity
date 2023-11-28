package com.example.frivolity.network.models

data class ApiItem(
    val itemID: Int,
    val lastUploadTime: Long,
    val worldID: Int,
    val worldName: String
    )