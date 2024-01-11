package com.example.frivolity.repository

import com.example.frivolity.network.UniversalisApi
import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiWorld
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class XIVServersRepository @Inject constructor(
    private val universalisApi: UniversalisApi,
    repositoryScope: CoroutineScope,
) {
    suspend fun getDataCenters(): List<ApiDataCenter> {
        return universalisApi.getDataCenters()
    }

    suspend fun getWorlds(): List<ApiWorld> {
        return universalisApi.getWorlds()
    }

    val dcList =
        repositoryScope.launch(Dispatchers.IO) {
            getDataCenters()
        }


}