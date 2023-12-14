package com.example.frivolity.ui.screens

import androidx.lifecycle.SavedStateHandle
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
class ItemDetailScreenViewModel @Inject constructor(
    repository: FrivolityRepository,
    detailSavedStateHandle: SavedStateHandle

) : ViewModel() {
    private val itemId: Int =
        checkNotNull(detailSavedStateHandle[DetailsDestination.itemIdArg])
    private val worldName: String =
        checkNotNull(detailSavedStateHandle[DetailsDestination.worldNameArg])

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val item = repository.getMarketItemDetails(worldName, itemId)
            _internalScreenStateFlow.update {
                ItemDetailScreenState(
                   item = item
                )
            }
        }
    }

    private val _internalScreenStateFlow =
        MutableStateFlow(value = ItemDetailScreenState.EMPTY)
    val screenStateFlow: StateFlow<ItemDetailScreenState> = _internalScreenStateFlow.asStateFlow()
}