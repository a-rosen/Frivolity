package com.example.frivolity.repository

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiItemDetail
import com.example.frivolity.network.models.universalisapi.ApiWorld

interface FrivolityRepository {

    suspend fun getDataCenters(): List<ApiDataCenter>
    suspend fun getWorlds(): List<ApiWorld>
    suspend fun getItemDetails(): ApiItemDetail
}