package com.example.frivolity.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.repository.DataStoreStorage
import com.example.frivolity.repository.FrivolityRepository
import com.example.frivolity.repository.XIVServersRepository
import com.example.frivolity.ui.Asynchronous
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
    private val serverRepository: XIVServersRepository,
    private val dataStore: DataStoreStorage,
) : ViewModel() {
    private val _internalScreenStateFlow =
        MutableStateFlow(value = MainScreenState.EMPTY)
    val screenStateFlow: StateFlow<MainScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getDcListFromStorage()
            getWorldsListFromStorage()
            getSelectedDc()
            getSelectedWorld()
        }
    }

    fun selectDataCenter(dcName: String) {
        val dcListFromState = _internalScreenStateFlow.value.dataCentersList
        if (dcListFromState is Asynchronous.Success) {
            val dcToSelect = dcListFromState
                .resultData
                .firstOrNull {
                    it.name == dcName
                }

            _internalScreenStateFlow.update {
                it.copy(
                    selectedDC = dcToSelect
                )
            }
        }    }


    fun selectWorld(worldName: String) {
        val worldsFromState = _internalScreenStateFlow.value.worldsList
        if (worldsFromState is Asynchronous.Success) {
            val worldToSelect = worldsFromState
                .resultData
                .firstOrNull {
                    it.name == worldName
                }

            _internalScreenStateFlow.update {
                it.copy(
                    selectedWorld = worldToSelect
                )
            }
        }
    }

    fun saveSelectedServer(selectedDcName: String, selectedWorldName: String) {
        dataStore.saveSelectedServer(selectedDcName, selectedWorldName)
    }

    fun updateSearchBoxText(inputText: String) {
        _internalScreenStateFlow.update {
            it.copy(searchBoxText = inputText)
        }
    }

    fun submitSearch(inputText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _internalScreenStateFlow.update {
                it.copy(searchResults = networkRepository.itemSearchByString(inputText).results)
            }
        }
    }

    private suspend fun getDcListFromStorage() {
        _internalScreenStateFlow.update {
            it.copy(
                dataCentersList = Asynchronous.Loading()
            )
        }

        serverRepository
            .dataCentersFromServerKing
            .collect { listFromStorage ->
                _internalScreenStateFlow.update {
                    it.copy(dataCentersList = Asynchronous.Success(listFromStorage))

                }
            }
    }

    private suspend fun getWorldsListFromStorage() {
        _internalScreenStateFlow.update {
            it.copy(
                worldsList = Asynchronous.Loading()
            )
        }

        serverRepository
            .worldsFromServerKing
            .collect { listFromStorage ->
                _internalScreenStateFlow.update {
                    it.copy(worldsList = Asynchronous.Success(listFromStorage))
                }

            }
    }


    private fun getSelectedDc() {
        val dcListFromState = _internalScreenStateFlow.value.dataCentersList
        viewModelScope.launch {
            dataStore
                .storedSelectedDcFlow
                .collect { name ->
                    if (dcListFromState is Asynchronous.Success) {
                        val selectedDcFromStoredName = dcListFromState
                            .resultData
                            .firstOrNull { it.name == name }

                        _internalScreenStateFlow.update {
                            it.copy(selectedDC = selectedDcFromStoredName)
                        }
                    }
                }
        }
    }

    private fun getSelectedWorld() {
        val worldsListFromState = _internalScreenStateFlow.value.worldsList
        viewModelScope.launch(Dispatchers.IO) {
            dataStore
                .storedSelectedWorldFlow
                .collect { name ->
                    if (worldsListFromState is Asynchronous.Success) {
                        val selectedWorldFromStoredName = worldsListFromState
                            .resultData
                            .firstOrNull { it.name == name }

                        _internalScreenStateFlow.update {
                            it.copy(
                                selectedWorld = selectedWorldFromStoredName
                            )
                        }
                    }

                }
        }
    }
}

