package com.example.frivolity.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.repository.DataStoreRepository
import com.example.frivolity.repository.XIVServersRepository
import com.example.frivolity.ui.Asynchronous
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val serverRepository: XIVServersRepository,
    private val dataStore: DataStoreRepository,
) : ViewModel() {
    private val _internalScreenStateFlow = MutableStateFlow(value = SettingsScreenState.EMPTY)
    val screenStateFlow: StateFlow<SettingsScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getRawDcListFromApi()
            saveDcListToDataStore()

        }
    }

    suspend fun getDcListFromStore() {
        TODO("gotta get list from store, deserialize, use")
    }

    private suspend fun getRawDcListFromApi() {
        _internalScreenStateFlow.update {
            it.copy(
                dcListRaw = Asynchronous.Loading()
            )
        }
        serverRepository
            .dcRawFlow
            .catch { error -> handleError(error.message) }
            .collect { dcListRaw ->
                _internalScreenStateFlow.update {
                    it.copy(
                        dcListRaw = Asynchronous.Success(dcListRaw)
                    )
                }
            }
    }

    private suspend fun saveDcListToDataStore() {
        screenStateFlow.collect {
            if (it.dcListRaw is Asynchronous.Success) {
                val results = (_internalScreenStateFlow
                    .value
                    .dcListRaw as Asynchronous.Success<String>)
                    .resultData
                dataStore.saveDcList(results)

            }
        }
    }


    private fun handleError(message: String?) {
        Log.e("SSVM Error", "Error occurred: $message")
        _internalScreenStateFlow.update {
            it.copy(
                dcListRaw = Asynchronous.Error(message ?: "Unknown Error")
            )
        }
    }

}
