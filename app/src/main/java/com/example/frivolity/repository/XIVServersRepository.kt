package com.example.frivolity.repository

import com.example.frivolity.network.UniversalisApi
import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class XIVServersRepository @Inject constructor(
    private val universalisApi: UniversalisApi,
    private val storage: DataStoreStorage,
    private val repositoryScope: CoroutineScope,
    private val gson: Gson
) {
    init {
        setupDataCenterState()
    }

    private val _whatTheRepositoryUnderstandsTheDataCenterSituationToBe = MutableStateFlow<List<ApiDataCenter>>(
        emptyList()
    )
    val viewModelsShouldAskForThis = _whatTheRepositoryUnderstandsTheDataCenterSituationToBe.asStateFlow()

    private suspend fun getListOfDcsFromNetwork(): String {
        val dcsJson =  universalisApi.getDcsRaw().string()
        saveNetworkListToEmptyStore(dcsJson)
        return dcsJson
    }

    private fun saveNetworkListToEmptyStore(dcListJson: String) {

    }

    private fun setupDataCenterState() {
        storage
            .storedDcListJsonFlow
            .onEach { dcStringFromDataStore ->
                if (dcStringFromDataStore.isEmpty()) {
                    val dcStringFromNetwork = getListOfDcsFromNetwork()
                    _whatTheRepositoryUnderstandsTheDataCenterSituationToBe.value =
                        dcStringFromNetwork.turnIntoAStructuredList()
                } else {
                    _whatTheRepositoryUnderstandsTheDataCenterSituationToBe.value =
                        dcStringFromDataStore.turnIntoAStructuredList()
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(repositoryScope)
    }

    private fun String.turnIntoAStructuredList(): List<ApiDataCenter> {
        return gson
            .fromJson(this, Array<ApiDataCenter>::class.java)
            .toList()
    }
}

