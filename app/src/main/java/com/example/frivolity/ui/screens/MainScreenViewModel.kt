package com.example.frivolity.ui.screens

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiWorld
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
    private val repository: FrivolityRepository,
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {
    private val _internalScreenStateFlow =
        MutableStateFlow<MainScreenState>(value = MainScreenState.EMPTY)
    val screenStateFlow: StateFlow<MainScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _internalScreenStateFlow.update {
                return@update MainScreenState(
                    repository.getDataCenters(),
                    repository.getWorlds(),
                    it.recentlyUpdatedList,
                    it.selectedDC,
                    it.selectedWorld
                )
            }
        }
    }

    fun selectDataCenter(dcName: String) {
        val dcToSelect = _internalScreenStateFlow
            .value
            .dataCentersList.firstOrNull() {
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
            .worldsList.firstOrNull() {
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

    fun saveSelectedServer(selectedDC: ApiDataCenter?, selectedWorld: ApiWorld?) {
    }
}