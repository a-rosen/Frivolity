package com.example.frivolity.ui.screens

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frivolity.repository.DataStoreStorage
import com.example.frivolity.repository.FrivolityRepository
import com.example.frivolity.repository.XIVServersRepository
import com.example.frivolity.ui.Asynchronous
import com.example.frivolity.ui.models.SortMethods
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
class ItemDetailScreenViewModel @Inject constructor(
    private val networkRepository: FrivolityRepository,
    private val serverRepository: XIVServersRepository,
    private val storage: DataStoreStorage,
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
            getSelectedServer()
            getMarketItemDetails(worldName, itemId)
            getFullItemDetails(itemId)

            if (screenStateFlow.value.currentLogicalDc is Asynchronous.Success) {
                findCheapestPrices()
            }
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

    fun toggleWorldList() {
        _internalScreenStateFlow.update {
            val newShouldShowWorldList = !it.shouldShowWorldList
            it.copy(
                shouldShowWorldList = newShouldShowWorldList
            )
        }
    }

    // this is verbatim same function as MSVM, can we do this some other way?
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
                    if (logicalDc == null) {
                        it.copy(
                            currentLogicalDc = Asynchronous.Error
                                ("Couldn't find a DC with that name")
                        )
                    } else {
                        it.copy(
                            currentLogicalDc = Asynchronous.Success(logicalDc)
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
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

    private fun findCheapestPrices() {
        findCheapestPricesInRegion()
        findCheapestPricesOnDc()
    }

    private fun findCheapestPricesInRegion() {
        val state = _internalScreenStateFlow.value
        val region = state.currentLogicalDc
        val itemId = state.marketItemDetail.itemID

        _internalScreenStateFlow.update {
            it.copy(
                cheapestUnitPriceRegion = Asynchronous.Loading(),
                cheapestTotalPriceRegion = Asynchronous.Loading(),
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val allMarketItemPricesInRegion = networkRepository
                .getMarketItemPrices(region.toString(), itemId)
                .listings

            // move these calculations to a higher level - maybe some kind of repository can do this,
            // also make another model that isn't directly tied to the API

            val cheapestTotalPriceInRegion = allMarketItemPricesInRegion.minOfOrNull { it.total }
            val listingWithCheapestTotalPriceInRegion =
                allMarketItemPricesInRegion.firstOrNull { it.total == cheapestTotalPriceInRegion }
            val cheapestUnitPriceInRegion =
                allMarketItemPricesInRegion.minOfOrNull { it.pricePerUnit }
            val listingWithCheapestUnitPriceInRegion =
                allMarketItemPricesInRegion.firstOrNull { it.pricePerUnit == cheapestUnitPriceInRegion }

            val cheapestTotalPriceRegion = listingWithCheapestTotalPriceInRegion?.let {
                Asynchronous.Success(it)
            } ?: Asynchronous.Error("Api is borked")

            val cheapestUnitPriceRegion = listingWithCheapestUnitPriceInRegion?.let {
                Asynchronous.Success(it)
            } ?: Asynchronous.Error("Api is borked")

            _internalScreenStateFlow.update {
                it.copy(
                    cheapestTotalPriceRegion = cheapestTotalPriceRegion,
                    cheapestUnitPriceRegion = cheapestUnitPriceRegion
                )
            }
        }
    }


    private fun findCheapestPricesOnDc() {
        val state = _internalScreenStateFlow.value
        val dc = if (state.currentLogicalDc is Asynchronous.Success) {
            state.currentLogicalDc.resultData.region
        } else {
            TODO()
        }
        val itemId = state.marketItemDetail.itemID

        _internalScreenStateFlow.update {
            it.copy(
                cheapestTotalPriceDc = Asynchronous.Loading(),
                cheapestUnitPriceDc = Asynchronous.Loading()
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val allPricesDc = networkRepository
                .getMarketItemPrices(dc, itemId)
                .listings

            val cheapestTotalDc = allPricesDc.minOfOrNull { it.total }
            val cheapestTotalListingDc = allPricesDc.firstOrNull { it.total == cheapestTotalDc }
            val cheapestUnitDc = allPricesDc.minOfOrNull { it.pricePerUnit }
            val cheapestUnitListingDc =
                allPricesDc.firstOrNull { it.pricePerUnit == cheapestUnitDc }

            if (cheapestTotalListingDc == null || cheapestUnitListingDc == null) {
                _internalScreenStateFlow.update {
                    it.copy(
                        cheapestTotalPriceRegion = Asynchronous.Error("Api is borked"),
                        cheapestUnitPriceRegion = Asynchronous.Error("Api is borked")
                    )
                }
            } else {
                _internalScreenStateFlow.update {
                    it.copy(
                        cheapestTotalPriceDc = Asynchronous.Success(
                            cheapestTotalListingDc
                        ),
                        cheapestUnitPriceDc = Asynchronous.Success(
                            cheapestUnitListingDc
                        ),
                    )
                }
            }
        }
    }

    private fun handleError(message: String?) {
        Log.e("IDSVM Error", "Error occurred: $message")
    }
}