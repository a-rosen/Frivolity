package com.example.frivolity.repository

import android.util.Log
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
            getDcsRaw()
        }
    }

    val dcFlow = flow {
        val dcList = getDcList()
        emit(dcList)
    }

    val dcRawFlow = flow {
        val dcListRaw = getDcsRaw()
        emit(dcListRaw)
    }

    val worldFlow = flow {
        val worldList = getWorldList()
        emit(worldList)
    }

    private suspend fun getWorldList(): List<ApiWorld> {
        return universalisApi.getWorlds()
    }

    private suspend fun getDcList(): List<ApiDataCenter> {
        return universalisApi.getDataCenters()
    }

    private suspend fun getDcsRaw(): String {
        return try {
            universalisApi.getDcsRaw().string()
        } catch (ex: Exception) {
            Log.e("oops", "network error: ${ex.message}")
            "Some other string lol"
        }
    }


}