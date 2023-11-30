package com.example.frivolity.network.models.universalisapi

data class ApiItem(
    val itemID: Int,
    val lastUploadTime: Long,
    val worldID: Int,
    val worldName: String
    )