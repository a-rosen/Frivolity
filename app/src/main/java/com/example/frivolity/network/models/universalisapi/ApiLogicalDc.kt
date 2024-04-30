package com.example.frivolity.network.models.universalisapi

data class ApiLogicalDc(
    val name: String,
    val region: String,
    val worlds: List<ApiWorld>
)