package com.example.frivolity.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.repository.DataStoreRepository
import com.example.frivolity.repository.FrivolityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val networkRepository: FrivolityRepository,
    private val dataStore: DataStoreRepository,
) : ViewModel() {
    private val _internalScreenStateFlow =
        MutableStateFlow<MainScreenState>(value = MainScreenState.EMPTY)
    val screenStateFlow: StateFlow<MainScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _internalScreenStateFlow.update {
                return@update MainScreenState(
                    networkRepository.getDataCenters(),
                    networkRepository.getWorlds(),
                    it.recentlyUpdatedList,
                    it.selectedDC,
                    it.selectedWorld
                )

            }
            getSelectedServer()

        }
    }

    fun selectDataCenter(dcName: String) {
        val dcToSelect = _internalScreenStateFlow
            .value
            .dataCentersList.firstOrNull {
                it.name == dcName
            }


        _internalScreenStateFlow.update {
            return@update MainScreenState(
                it.dataCentersList,
                it.worldsList,
                it.recentlyUpdatedList,
                dcToSelect,
                null
            )
        }
    }

    fun selectWorld(worldName: String) {
        val worldToSelect = _internalScreenStateFlow
            .value
            .worldsList.firstOrNull {
                it.name == worldName
            }

        _internalScreenStateFlow.update {
            return@update MainScreenState(
                it.dataCentersList,
                it.worldsList,
                it.recentlyUpdatedList,
                it.selectedDC,
                worldToSelect
            )
        }
    }

    fun saveSelectedServer(selectedDcName: String, selectedWorldName: String) {
        dataStore.saveSelectedServer(selectedDcName, selectedWorldName)
    }

    private fun getSelectedServer() {
        viewModelScope.launch {
            dataStore.storedDcFlow.collect { name ->
                val dcFromStored = _internalScreenStateFlow.value.dataCentersList
                    .firstOrNull { it.name == name }
                _internalScreenStateFlow.update {
                    return@update MainScreenState(
                        it.dataCentersList,
                        it.worldsList,
                        it.recentlyUpdatedList,
                        dcFromStored,
                        it.selectedWorld
                    )
                }
            }

            dataStore.storedWorldFlow.collect { name ->
                val worldFromStored = _internalScreenStateFlow.value.worldsList
                    .firstOrNull { it.name == name }
                _internalScreenStateFlow.update {
                    return@update MainScreenState(
                        it.dataCentersList,
                        it.worldsList,
                        it.recentlyUpdatedList,
                        it.selectedDC,
                        worldFromStored
                    )
                }
            }
        }
    }
}
