package com.example.frivolity.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.repository.DataStoreRepository
import com.example.frivolity.repository.FrivolityRepository
import com.example.frivolity.repository.XIVServersRepository
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
    private val dataStore: DataStoreRepository,
) : ViewModel() {
    private val _internalScreenStateFlow =
        MutableStateFlow(value = MainScreenState.EMPTY)
    val screenStateFlow: StateFlow<MainScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getDcListFromApi()
            getWorldListFromApi()
            getSelectedDc()
            getSelectedWorld()
        }
    }

    private suspend fun getDcListFromApi() {
        serverRepository.dcFlow.collect { collectedList ->
            _internalScreenStateFlow.update {
                it.copy(dataCentersList = collectedList)
            }
        }
    }

    private suspend fun getWorldListFromApi() {
        serverRepository.worldFlow.collect { collectedList ->
            _internalScreenStateFlow.update {
                it.copy(worldsList = collectedList)
            }
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
                null,
                it.searchBoxText,
                it.searchResults,

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
                worldToSelect,
                it.searchBoxText,
                it.searchResults,

                )
        }
    }

    fun saveSelectedServer(selectedDcName: String, selectedWorldName: String) {
        dataStore.saveSelectedServer(selectedDcName, selectedWorldName)
    }

    fun updateSearchBoxText(inputText: String) {
        _internalScreenStateFlow.update {
            MainScreenState(
                it.dataCentersList,
                it.worldsList,
                it.recentlyUpdatedList,
                it.selectedDC,
                it.selectedWorld,
                inputText,
                it.searchResults,

                )
        }
    }

    fun submitSearch(inputText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _internalScreenStateFlow.update {
                return@update MainScreenState(
                    it.dataCentersList,
                    it.worldsList,
                    it.recentlyUpdatedList,
                    it.selectedDC,
                    it.selectedWorld,
                    it.searchBoxText,
                    networkRepository.itemSearchByString(inputText).results,
                )
            }
        }
    }

    private fun getSelectedDc() {
        viewModelScope.launch {
            dataStore.storedDcFlow.collect { name ->
                val dcFromStored = _internalScreenStateFlow.value.dataCentersList
                    .firstOrNull { it.name == name }

                _internalScreenStateFlow.update {
                    MainScreenState(
                        it.dataCentersList,
                        it.worldsList,
                        it.recentlyUpdatedList,
                        dcFromStored,
                        it.selectedWorld,
                        it.searchBoxText,
                        it.searchResults,

                        )
                }
            }
        }
    }

    private fun getSelectedWorld() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.storedWorldFlow.collect { name ->
                val worldFromStored = _internalScreenStateFlow.value.worldsList
                    .firstOrNull { it.name == name }

                _internalScreenStateFlow.update {
                    MainScreenState(
                        it.dataCentersList,
                        it.worldsList,
                        it.recentlyUpdatedList,
                        it.selectedDC,
                        worldFromStored,
                        it.searchBoxText,
                        it.searchResults,
                    )
                }
            }
        }
    }
}

