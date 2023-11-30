package com.example.frivolity.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val repository: FrivolityRepository
) : ViewModel() {
    private val _internalScreenStateFlow =
        MutableStateFlow<MainScreenState>(value = MainScreenState.EMPTY)
    val screenStateFlow: StateFlow<MainScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _internalScreenStateFlow.update {
                return@update MainScreenState(
                    repository.getDataCenters(),
                    it.worldsList,
                    it.recentlyUpdatedList,
                    it.selectedDC,
                    it.selectedWorld
                )
            }
        }

    }

    fun requestWorlds() {
        viewModelScope.launch(Dispatchers.IO) {
            _internalScreenStateFlow.update {
                return@update MainScreenState(
                    it.dataCentersList,
                    repository.getWorlds(),
                    it.recentlyUpdatedList,
                    it.selectedDC,
                    it.selectedWorld,
                )
            }
        }
    }

//
//    fun requestRecentlyUpdated(world: String, dcName: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _internalScreenStateFlow.update {
//                return@update MainScreenState(
//                    it.dataCentersList,
//                    it.worldsList,
//                    api.getRecentlyUpdated(
//                        world,
//                        dcName,
//                        5
//                    ),
//                    it.selectedDC,
//                    it.selectedWorld
//                )
//            }
//        }
//    }

    fun toggleDropdown() {

    }
}