package com.example.frivolity.network.models

import com.google.gson.annotations.SerializedName

data class ApiWorldList(
    @SerializedName("Pagination")
    val pagination: ApiPagination,

    @SerializedName("Results")
    val results: List<ApiWorld>
)