package com.example.frivolity.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiWorld
import com.example.frivolity.repository.FrivolityRepository
import com.example.frivolity.repository.XIVServersRepository
import com.example.frivolity.ui.models.SortMethods
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
    private val networkRepository: FrivolityRepository,
    private val serverRepository: XIVServersRepository,
    detailSavedStateHandle: SavedStateHandle

) : ViewModel() {
    private val itemId: Int =
        checkNotNull(detailSavedStateHandle[DetailsDestination.itemIdArg])
    private val worldName: String =
        checkNotNull(detailSavedStateHandle[DetailsDestination.worldNameArg])
    private var dcList: List<ApiDataCenter> = listOf()
    private var worldList: List<ApiWorld> = listOf()

    private val _internalScreenStateFlow =
        MutableStateFlow(value = ItemDetailScreenState.EMPTY)
    val screenStateFlow: StateFlow<ItemDetailScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val marketItem = networkRepository.getMarketItemDetails(worldName, itemId)
            val itemDetail = networkRepository.getFullItemDetails(itemId)
            serverRepository.dcFlow.collect { list -> dcList = list}
            serverRepository.worldFlow.collect { list -> worldList = list }

            _internalScreenStateFlow.update {
                ItemDetailScreenState(
                    marketItemDetail = marketItem,
                    itemDetail = itemDetail,
                    regionToSearch = "North-America",
                    it.cheapestTotalPrice,
                    it.cheapestUnitPrice,
                    it.sortMethod,
                    it.showHqOnly,
                    it.shouldShowDropdown,
                    dcList,
                    worldList
                )
            }
            findCheapestPrices()
        }
    }

    fun toggleDropdown() {
        _internalScreenStateFlow.update {
            ItemDetailScreenState(
                it.marketItemDetail,
                it.itemDetail,
                it.regionToSearch,
                it.cheapestTotalPrice,
                it.cheapestUnitPrice,
                it.sortMethod,
                it.showHqOnly,
                !it.shouldShowDropdown,
                it.dcList,
                it.worldList
            )
        }

    }

    fun sortByTotal() {
        _internalScreenStateFlow.update {
            ItemDetailScreenState(
                it.marketItemDetail,
                it.itemDetail,
                it.regionToSearch,
                it.cheapestTotalPrice,
                it.cheapestUnitPrice,
                SortMethods.TOTAL,
                it.showHqOnly,
                it.shouldShowDropdown,
                it.dcList,
                it.worldList
            )
        }

    }

    fun sortByUnit() {
        _internalScreenStateFlow.update {
            ItemDetailScreenState(
                it.marketItemDetail,
                it.itemDetail,
                it.regionToSearch,
                it.cheapestTotalPrice,
                it.cheapestUnitPrice,
                SortMethods.UNIT,
                it.showHqOnly,
                it.shouldShowDropdown,
                it.dcList,
                it.worldList
            )
        }
    }

    fun filterHq() {
        _internalScreenStateFlow.update {
            ItemDetailScreenState(
                it.marketItemDetail,
                it.itemDetail,
                it.regionToSearch,
                it.cheapestTotalPrice,
                it.cheapestUnitPrice,
                it.sortMethod,
                !it.showHqOnly,
                it.shouldShowDropdown,
                it.dcList,
                it.worldList
            )
        }

    }

    private fun findCheapestPrices() {
        viewModelScope.launch(Dispatchers.IO) {
            val allPrices = networkRepository
                .getMarketItemPrices(_internalScreenStateFlow.value.regionToSearch, itemId)
                .listings

            val cheapestTotal = allPrices.minOfOrNull { it.total }
            val cheapestTotalListing = allPrices.firstOrNull() { it.total == cheapestTotal }
            val cheapestUnit = allPrices.minOfOrNull { it.pricePerUnit }
            val cheapestUnitListing = allPrices.firstOrNull() { it.pricePerUnit == cheapestUnit }

            _internalScreenStateFlow.update {
                ItemDetailScreenState(
                    it.marketItemDetail,
                    it.itemDetail,
                    it.regionToSearch,
                    cheapestTotalListing,
                    cheapestUnitListing,
                    it.sortMethod,
                    it.showHqOnly,
                    it.shouldShowDropdown,
                    it.dcList,
                    it.worldList
                )
            }
        }
    }
}