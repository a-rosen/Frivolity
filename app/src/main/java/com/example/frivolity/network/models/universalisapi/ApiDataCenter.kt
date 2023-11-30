package com.example.frivolity.network.models.universalisapi

data class ApiDataCenter(
    val name: String,
    val region: String,
    val worlds: List<Int>
)