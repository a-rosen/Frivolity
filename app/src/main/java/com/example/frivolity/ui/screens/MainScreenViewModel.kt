package com.example.frivolity.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.repository.DataStoreStorage
import com.example.frivolity.repository.FrivolityRepository
import com.example.frivolity.repository.XIVServersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val networkRepository: FrivolityRepository,
    private val serverRepository: XIVServersRepository,
    private val storage: DataStoreStorage,
) : ViewModel() {
    private val _internalScreenStateFlow =
        MutableStateFlow(value = MainScreenState.EMPTY)
    val screenStateFlow: StateFlow<MainScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getSelectedServer()
        }
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

    private fun getSelectedServer() {
        combine(
            storage.storedSelectedDcFlow,
            serverRepository.logicalDcsFlow
        ) { dcName, logicalDcList ->
            logicalDcList
                .firstOrNull { it.name == dcName }
        }
            .onEach { logicalDc ->
                _internalScreenStateFlow.update {
                    it.copy(
                        selectedServer = logicalDc
                    )
                }
                getSelectedWorld()
            }
            .launchIn(viewModelScope)

    }

    private fun getSelectedWorld() {
        storage.storedSelectedWorldFlow
            .onEach { storedWorldName ->
                val selectedApiWorldFromState = _internalScreenStateFlow
                    .value
                    .selectedServer
                    ?.worlds
                    ?.firstOrNull { apiWorld -> apiWorld.name == storedWorldName }

                _internalScreenStateFlow.update {
                    it.copy(
                        selectedWorld = selectedApiWorldFromState
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}

