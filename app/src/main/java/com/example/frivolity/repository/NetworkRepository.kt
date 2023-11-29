package com.example.frivolity.repository

import com.example.frivolity.network.UniversalisApi
import com.example.frivolity.network.XIVApi
import com.example.frivolity.network.models.ApiDataCenter
import com.example.frivolity.network.models.ApiWorldList
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val universalisApi: UniversalisApi,
    private val xivApi: XIVApi,
) : FrivolityRepository {

    override suspend fun getDataCenters(): List<ApiDataCenter> {
        return universalisApi.getDataCenters()
    }

    override suspend fun getWorlds(): ApiWorldList {
        return xivApi.getWorlds()
    }
}