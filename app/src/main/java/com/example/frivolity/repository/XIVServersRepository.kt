package com.example.frivolity.repository

import com.example.frivolity.network.UniversalisApi
import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiWorld
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class XIVServersRepository @Inject constructor(
    private val universalisApi: UniversalisApi,
    repositoryScope: CoroutineScope,
) {
    init {
        repositoryScope.launch(Dispatchers.IO) {
            getDcList()
            getWorldList()
        }
    }

    val dcFlow = flow {
        val dcList = getDcList()
        emit(dcList)
    }

    val worldFlow = flow {
        val worldList = getWorldList()
        emit(worldList)
    }

    suspend fun getWorldList(): List<ApiWorld> {
        return universalisApi.getWorlds()
    }

    suspend fun getDcList(): List<ApiDataCenter> {
        return universalisApi.getDataCenters()
    }


}