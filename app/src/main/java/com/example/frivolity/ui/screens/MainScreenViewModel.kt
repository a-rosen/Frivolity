package com.example.frivolity.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.network.models.xivapi.asItemDetailDataRecord
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
        MutableStateFlow(value = MainScreenState.EMPTY)
    val screenStateFlow: StateFlow<MainScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            networkRepository.dataFlow
                .collect { results ->
                    _internalScreenStateFlow.update {
                        return@update MainScreenState(
                            networkRepository.getDataCenters(),
                            networkRepository.getWorlds(),
                            it.recentlyUpdatedList,
                            it.selectedDC,
                            it.selectedWorld,
                            it.searchBoxText,
                            it.searchResults,
                            it.searchResultsDetail
                        )
                    }
                    getSelectedDc()
                    getSelectedWorld()
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
                it.searchResultsDetail

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
                it.searchResultsDetail

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
                it.searchResultsDetail

            )
        }
    }

    fun submitSearch(inputText: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val searchResults = networkRepository.itemSearchByString(inputText).results.map {
                networkRepository.getFullItemDetails(it.id)
                .asItemDetailDataRecord()
            }

            _internalScreenStateFlow.update {
                return@update MainScreenState(
                    it.dataCentersList,
                    it.worldsList,
                    it.recentlyUpdatedList,
                    it.selectedDC,
                    it.selectedWorld,
                    it.searchBoxText,
                    networkRepository.itemSearchByString(inputText).results,
                    searchResults
                )
            }
        }
    }

    fun getItemLevelById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            networkRepository.getItemLevelById(id)
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
                        it.searchResultsDetail

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
                        it.searchResultsDetail

                    )
                }
            }
        }
    }
}

