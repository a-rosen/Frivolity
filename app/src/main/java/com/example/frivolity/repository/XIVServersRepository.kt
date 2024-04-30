package com.example.frivolity.repository

import com.example.frivolity.network.UniversalisApi
import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiLogicalDc
import com.example.frivolity.network.models.universalisapi.ApiWorld
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
        setupWorldState()
    }

    private val _whatTheRepositoryUnderstandsTheDataCenterSituationToBe =
        MutableStateFlow<List<ApiDataCenter>>(
            emptyList()
        )
    private val _whatTheRepositoryUnderstandsTheWorldSituationToBe =
        MutableStateFlow<List<ApiWorld>>(
            emptyList()
        )

    val dataCentersFromServerKing =
        _whatTheRepositoryUnderstandsTheDataCenterSituationToBe.asStateFlow()
    val worldsFromServerKing =
        _whatTheRepositoryUnderstandsTheWorldSituationToBe.asStateFlow()

    val logicalDcsFlow = combine(
        dataCentersFromServerKing,
        worldsFromServerKing
    ) { centers, worlds ->
        return@combine centers
            .map { thisDc ->
                val worldsForThisDataCenter = worlds
                    .filter { thisDc.worldIds.contains(it.id) }
                return@map ApiLogicalDc(
                    name = thisDc.name,
                    region = thisDc.region,
                    worlds = worldsForThisDataCenter,
                )
            }
    }

    private suspend fun getListOfDcsFromNetwork(): String {
        val dcsJson = universalisApi.getDcsRaw().string()
        saveNetworkDcListToEmptyStore(dcsJson)
        return dcsJson
    }

    private suspend fun getListOfWorldsFromNetwork(): String {
        val worldsJson = universalisApi.getWorldsRaw().string()
        saveNetworkWorldListToEmptyStore(worldsJson)
        return worldsJson
    }

    private fun saveNetworkDcListToEmptyStore(dcListJson: String) {
        storage.saveDcList(dcListJson)
    }

    private fun saveNetworkWorldListToEmptyStore(worldsListJson: String) {
        storage.saveWorldsList(worldsListJson)
    }

    private fun setupDataCenterState() {
        storage
            .storedDcListJsonFlow
            .onEach { dcStringFromDataStore ->
                if (dcStringFromDataStore.isEmpty()) {
                    val dcStringFromNetwork = getListOfDcsFromNetwork()
                    _whatTheRepositoryUnderstandsTheDataCenterSituationToBe.value =
                        dcStringFromNetwork.turnIntoStructuredDcList()
                } else {
                    _whatTheRepositoryUnderstandsTheDataCenterSituationToBe.value =
                        dcStringFromDataStore.turnIntoStructuredDcList()
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(repositoryScope)
    }

    private fun setupWorldState() {
        storage
            .storedWorldsListJsonFlow
            .onEach { worldsStringFromDataStore ->
                if (worldsStringFromDataStore.isEmpty()) {
                    val worldsStringFromNetwork = getListOfWorldsFromNetwork()
                    _whatTheRepositoryUnderstandsTheWorldSituationToBe.value =
                        worldsStringFromNetwork.turnIntoStructuredWorldsList()
                } else {
                    _whatTheRepositoryUnderstandsTheWorldSituationToBe.value =
                        worldsStringFromDataStore.turnIntoStructuredWorldsList()
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(repositoryScope)
    }

    // is there a way to make these functions more generic?
    private fun String.turnIntoStructuredDcList(): List<ApiDataCenter> {
        return gson
            .fromJson(this, Array<ApiDataCenter>::class.java)
            .toList()
    }

    private fun String.turnIntoStructuredWorldsList(): List<ApiWorld> {
        return gson
            .fromJson(this, Array<ApiWorld>::class.java)
            .toList()
    }
}

// fix this so that the network call is made each time the app is launched

