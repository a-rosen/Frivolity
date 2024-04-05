package com.example.frivolity.ui.screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.repository.FrivolityRepository
import com.example.frivolity.repository.XIVServersRepository
import com.example.frivolity.ui.Asynchronous
import com.example.frivolity.ui.models.SortMethods
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
class ItemDetailScreenViewModel @Inject constructor(
    private val networkRepository: FrivolityRepository,
    private val serverRepository: XIVServersRepository,
    detailSavedStateHandle: SavedStateHandle

) : ViewModel() {
    private val _internalScreenStateFlow = MutableStateFlow(value = ItemDetailScreenState.EMPTY)
    val screenStateFlow: StateFlow<ItemDetailScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        val itemId: Int =
            checkNotNull(detailSavedStateHandle[DetailsDestination.itemIdArg])
        val worldName: String =
            checkNotNull(detailSavedStateHandle[DetailsDestination.worldNameArg])

        viewModelScope.launch(Dispatchers.IO) {
            getListOfWorlds(worldName)
            getListOfDataCenters()

            getMarketItemDetails(worldName, itemId)
            getFullItemDetails(itemId)

            findCheapestPrices()

            screenStateFlow.collect {
                if (it.dcList is Asynchronous.Success) {
                    findCurrentDcAndRegion()
                }
            }
        }
    }

    fun changeDc(dc: ApiDataCenter?) {
        _internalScreenStateFlow.update {
            it.copy(
                currentDc = dc,
            )
        }
    }

    fun sortByTotal() {
        _internalScreenStateFlow.update {
            it.copy(
                sortMethod = SortMethods.TOTAL
            )
        }
    }

    fun sortByUnit() {
        _internalScreenStateFlow.update {
            it.copy(
                sortMethod = SortMethods.UNIT
            )
        }
    }

    fun filterHq() {
        _internalScreenStateFlow.update {
            val newShowHqOnly = !it.showHqOnly
            it.copy(showHqOnly = newShowHqOnly)
        }
    }

    fun toggleStatsRow() {
        _internalScreenStateFlow.update {
            val newShouldShowStatsRow = !it.shouldShowStatsRow
            it.copy(
                shouldShowStatsRow = newShouldShowStatsRow
            )
        }
    }

    fun toggleDcList() {
        _internalScreenStateFlow.update {
            val newShouldShowDcList = !it.shouldShowDcList
            it.copy(
                shouldShowDcList = newShouldShowDcList
            )
        }
    }

    fun toggleWorldList() {
        _internalScreenStateFlow.update {
            val newShouldShowWorldList = !it.shouldShowWorldList
            it.copy(
                shouldShowWorldList = newShouldShowWorldList
            )
        }
    }

    private suspend fun getMarketItemDetails(worldName: String, itemId: Int) {
        try {
            val marketItem = networkRepository.getMarketItemDetails(worldName, itemId)
            _internalScreenStateFlow.update {
                it.copy(
                    marketItemDetail = marketItem
                )
            }
        } catch (error: Exception) {
            handleError(error.message)
        }
    }

    private suspend fun getFullItemDetails(itemId: Int) {
        val itemDetail = networkRepository.getFullItemDetails(itemId)
        _internalScreenStateFlow.update {
            it.copy(
                itemDetail = itemDetail
            )
        }
    }

    private suspend fun getListOfDataCenters() {
        _internalScreenStateFlow.update {
            it.copy(
                dcList = Asynchronous.Loading()
            )
        }

        serverRepository
            .dcFlow
            .catch { error -> handleError(error.message) }
            .collect { listFromNetwork ->
                _internalScreenStateFlow.update {
                    it.copy(dcList = Asynchronous.Success(listFromNetwork))
                }

            }
    }

    private suspend fun getListOfWorlds(worldName: String) {
        _internalScreenStateFlow.update {
            it.copy(
                worldList = Asynchronous.Loading()
            )
        }
        serverRepository
            .worldFlow
            .catch { error -> handleError(error.message) }
            .collect { listFromNetwork ->
                val worldId = listFromNetwork
                    .filter { it.name == worldName }
                    .map { it.id }
                    .firstOrNull()

                _internalScreenStateFlow.update {
                    it.copy(
                        worldList = Asynchronous.Success(listFromNetwork),
                        worldId = worldId,
                    )
                }

            }
    }

    private fun findCurrentDcAndRegion() {
        _internalScreenStateFlow.update { oldState ->
            val worldId = oldState.worldId

            if (worldId == null) {
                return@update oldState
            }

           when (oldState.dcList) {
                is Asynchronous.Loading ->
                    oldState.copy(
                        dcList = Asynchronous.Loading(),
                        worldList = Asynchronous.Loading()
                    )

                is Asynchronous.Success -> {
                    val newDc = oldState.dcList.resultData
                        .first { dc -> dc.worlds.contains(worldId) }
                    oldState.copy(
                        currentDc = newDc,
                        regionToSearch = newDc.region
                    )
                }

                is Asynchronous.Uninitialized ->
                    oldState.copy(
                        currentDc = ApiDataCenter("Uninitialized", "", listOf())
                    )

                is Asynchronous.Error -> oldState
            }
        }
    }

    private fun findCheapestPrices() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _internalScreenStateFlow.value
            val region = state.regionToSearch

            val allPrices = networkRepository
                .getMarketItemPrices(region, state.marketItemDetail.itemID)
                .listings

            val cheapestTotal = allPrices.minOfOrNull { it.total }
            val cheapestTotalListing = allPrices.firstOrNull() { it.total == cheapestTotal }
            val cheapestUnit = allPrices.minOfOrNull { it.pricePerUnit }
            val cheapestUnitListing = allPrices.firstOrNull() { it.pricePerUnit == cheapestUnit }

            _internalScreenStateFlow.update {
                it.copy(
                    cheapestTotalPrice = cheapestTotalListing,
                    cheapestUnitPrice = cheapestUnitListing,
                )
            }
        }
    }

    private fun handleError(message: String?) {
        Log.e("IDSVM Error", "Error occurred: $message")
        _internalScreenStateFlow.update {
            it.copy(
                dcList = Asynchronous.Error(message ?: "Unknown Error")
            )
        }
    }
}