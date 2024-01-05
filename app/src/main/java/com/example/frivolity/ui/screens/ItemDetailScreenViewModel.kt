package com.example.frivolity.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.repository.FrivolityRepository
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
    private val repository: FrivolityRepository,
    detailSavedStateHandle: SavedStateHandle

) : ViewModel() {
    private val itemId: Int =
        checkNotNull(detailSavedStateHandle[DetailsDestination.itemIdArg])
    private val worldName: String =
        checkNotNull(detailSavedStateHandle[DetailsDestination.worldNameArg])

    private val _internalScreenStateFlow =
        MutableStateFlow(value = ItemDetailScreenState.EMPTY)
    val screenStateFlow: StateFlow<ItemDetailScreenState> = _internalScreenStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val marketItem = repository.getMarketItemDetails(worldName, itemId)
            val itemDetail = repository.getFullItemDetails(itemId)

            _internalScreenStateFlow.update {
                ItemDetailScreenState(
                    marketItemDetail = marketItem,
                    itemDetail = itemDetail,
                    regionToSearch = "North-America",
                    it.cheapestTotalPrice,
                    it.cheapestUnitPrice,
                    it.sortMethod,
                    it.showHqOnly,
                    it.shouldShowDropdown
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
                !it.shouldShowDropdown
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
                it.shouldShowDropdown
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
                it.shouldShowDropdown
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
                it.shouldShowDropdown
            )
        }

    }

    private fun findCheapestPrices() {
        viewModelScope.launch(Dispatchers.IO) {
            val allPrices = repository
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
                    it.shouldShowDropdown
                )
            }
        }
    }
}