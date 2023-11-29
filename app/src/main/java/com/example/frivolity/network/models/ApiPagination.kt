package com.example.frivolity.network.models

data class ApiPagination (
    val Page: Int,
    val PageNext: Int,
    val PagePrev: Int?,
    val PageTotal: Int,
    val Results: Int,
    val ResultsPerPage: Int,
    val ResultsTotal: Int,
)