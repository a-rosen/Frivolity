package com.example.frivolity.ui.screens

import androidx.lifecycle.ViewModel
import com.example.frivolity.repository.XIVServersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val serverRepository: XIVServersRepository,
    private val repositoryScope: CoroutineScope
) : ViewModel() {
    private val _internalScreenStateFlow = MutableStateFlow(value = SettingsScreenState.EMPTY)
    val screenStateFlow: StateFlow<SettingsScreenState> = _internalScreenStateFlow.asStateFlow()


    init {
        serverRepository.logicalDcsFlow
            .onEach { logicalDcsList ->
                _internalScreenStateFlow.update {
                    it.copy(
                        logicalDcsList = logicalDcsList
                    )
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(repositoryScope)
    }

    fun selectDc(dcName: String) {
        _internalScreenStateFlow.update {
            it.copy(
                selectedDcName = dcName
            )
        }

    }
}
