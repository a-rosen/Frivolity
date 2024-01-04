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
                    it.cheapestPrice,
                    it.sortMethod,
                    it.showHqOnly
                )
            }
            findCheapestItemListing()
        }
    }

    fun sortByTotal() {
        _internalScreenStateFlow.update {
            ItemDetailScreenState(
                it.marketItemDetail,
                it.itemDetail,
                it.regionToSearch,
                it.cheapestPrice,
                SortMethods.TOTAL,
                it.showHqOnly
            )
        }
    }

    fun sortByUnit() {
        _internalScreenStateFlow.update {
            ItemDetailScreenState(
                it.marketItemDetail,
                it.itemDetail,
                it.regionToSearch,
                it.cheapestPrice,
                SortMethods.UNIT,
                it.showHqOnly
            )
        }
    }

    fun filterHq() {
        _internalScreenStateFlow.update {
            ItemDetailScreenState(
                it.marketItemDetail,
                it.itemDetail,
                it.regionToSearch,
                it.cheapestPrice,
                it.sortMethod,
                !it.showHqOnly
            )
        }

    }

    private fun findCheapestItemListing() {
        viewModelScope.launch(Dispatchers.IO) {
            val allPrices = repository
                .getMarketItemPrices(_internalScreenStateFlow.value.regionToSearch, itemId)
                .listings

            val cheapest = allPrices.minOfOrNull { it.total }
            val cheapestListing = allPrices.firstOrNull() { it.total == cheapest }

            _internalScreenStateFlow.update {ItemDetailScreenState(
                it.marketItemDetail,
                it.itemDetail,
                it.regionToSearch,
                cheapestListing,
                it.sortMethod,
                it.showHqOnly
            )
            }
        }
    }


}