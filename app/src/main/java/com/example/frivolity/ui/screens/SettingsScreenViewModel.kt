package com.example.frivolity.ui.screens

import androidx.lifecycle.ViewModel
import com.example.frivolity.repository.XIVServersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val serverRepository: XIVServersRepository,
) : ViewModel() {
    private val _internalScreenStateFlow = MutableStateFlow(value = SettingsScreenState.EMPTY)
    val screenStateFlow: StateFlow<SettingsScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        _internalScreenStateFlow.update {
            it.copy(
                dcList = serverRepository.dataCentersFromServerKing.value,
                worldsList = serverRepository.worldsFromServerKing.value,
            )
        }
    }
}
