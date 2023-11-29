package com.example.frivolity.repository

import com.example.frivolity.network.models.ApiDataCenter
import com.example.frivolity.network.models.ApiWorldList

interface FrivolityRepository {

    suspend fun getDataCenters(): List<ApiDataCenter>
    suspend fun getWorlds(): ApiWorldList
}