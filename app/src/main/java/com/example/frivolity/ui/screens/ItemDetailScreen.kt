package com.example.frivolity.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.frivolity.R
import com.example.frivolity.navigation.NavigationDestination
import com.example.frivolity.network.models.universalisapi.asUiListingDetail
import com.example.frivolity.network.models.xivapi.asUiItemDetail
import com.example.frivolity.ui.Asynchronous
import com.example.frivolity.ui.components.ButtonWithDropdown
import com.example.frivolity.ui.components.ChipRow
import com.example.frivolity.ui.components.ItemDetailHeader
import com.example.frivolity.ui.components.ListingListItem
import com.example.frivolity.ui.components.StatsRow
import com.example.frivolity.ui.models.SortMethods

object DetailsDestination : NavigationDestination {
    override val route = "ItemDetailScreen"
    const val worldNameArg = "worldName"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$worldNameArg}/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ItemDetailScreen(
    state: ItemDetailScreenState,
    viewModel: ItemDetailScreenViewModel,
    navigateBack: () -> Unit,
    changeServer: (String, Int) -> Unit
) {

    val item = state.marketItemDetail
    val itemDetail = state.itemDetail

    val listings = item.listings.map {
        it.asUiListingDetail()
    }
    val listingsByTotal = listings.sortedBy {
        it.total
    }
    val listingsByUnit = listings.sortedBy {
        it.pricePerUnit
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { navigateBack() },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "back button"
                            )
                        }
                    )
                },
                actions = {
                    ButtonWithDropdown(
                        icon = R.drawable.ic_group,
                        iconDescription = "Switch DC",
                        menuItems = when (state.dcList) {
                            is Asynchronous.Uninitialized ->
                                listOf()
                            is Asynchronous.Loading ->
                                listOf("Loading...")
                            is Asynchronous.Error ->
                              listOf("Not Found")
                            is Asynchronous.Success ->
                                state.dcList.resultData
                                    .filter { it.worlds[0] < 1000 }
                                    .map { dc -> dc.name }
                        },
                        onItemClicked = { item ->
                            when (state.dcList) {
                                is Asynchronous.Uninitialized -> {}
                                is Asynchronous.Loading -> {}
                                is Asynchronous.Error -> {}
                                is Asynchronous.Success ->
                                    viewModel.changeDc(state
                                        .dcList
                                        .resultData
                                        .firstOrNull { it.name == item})
                            }

                            viewModel.toggleDcList()
                            viewModel.toggleWorldList()
                        },
                        onIconClicked = { viewModel.toggleDcList() },
                        expanded = state.shouldShowDcList
                    )
                    if (state.worldList !is Asynchronous.Loading) {
                        ButtonWithDropdown(
                            icon = R.drawable.ic_world,
                            iconDescription = "Switch World",
                            menuItems = when (state.worldList) {
                                is Asynchronous.Uninitialized -> listOf("Uninitialized")
                                is Asynchronous.Loading -> listOf("Loading...")
                                is Asynchronous.Error -> listOf("Error")
                                is Asynchronous.Success ->
                                    state.worldList.resultData
                                        .filter { state.currentDc?.worlds?.contains(it.id) ?: false }
                                        .map { world -> world.name }
                            },
                            onItemClicked = { changeServer(it, item.itemID) },
                            onIconClicked = { viewModel.toggleWorldList() },
                            expanded = state.shouldShowWorldList
                        )
                    }


                },
                title = {
                    Text(
                        text = "Listings Detail:${state.currentDc?.name} ${item.worldName}",
                        style = MaterialTheme.typography.labelMedium,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                modifier = Modifier
            )
        },
        bottomBar = {
            NavigationBar {

            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            if (itemDetail != null) {
                ItemDetailHeader(
                    itemDetail = itemDetail.asUiItemDetail() ,
                    onDropdownClick = { viewModel.toggleStatsRow() },
                )
            }

            AnimatedVisibility(visible = state.shouldShowStatsRow) {
                StatsRow(
                    state = state,
                    iLevel = itemDetail?.iLevel ?: 0,
                    levelToEquip = itemDetail?.levelToEquip ?: 0,
                )
            }

            ChipRow(
                state = state,
                sortLabel = if (state.sortMethod == SortMethods.UNIT) {
                    "Total Sort"
                } else {
                    "Unit Sort"
                },
                onSortClick = {
                    if (state.sortMethod == SortMethods.UNIT) {
                        viewModel.sortByTotal()
                    } else {
                        viewModel.sortByUnit()
                    }
                },
                onHqOnlyClick = { viewModel.filterHq() },
            )
            LazyColumn(
            ) {
                if (state.sortMethod == SortMethods.TOTAL) {
                    items(listingsByTotal) {
                        if (it.hq || !state.showHqOnly) {
                            ListingListItem(
                                headlineText = "%,d".format(it.total),
                                supportingText = "%,d".format(it.quantity) + " x " + "%,d".format(it.pricePerUnit),
                                isHq = it.hq
                            )
                        }
                    }
                } else {
                    items(listingsByUnit) {
                        if (it.hq || !state.showHqOnly) {
                            ListingListItem(
                                headlineText = "%,d".format(it.pricePerUnit),
                                supportingText = "%,d".format(it.quantity) + " for " + "%,d".format(
                                    it.total
                                ),
                                isHq = it.hq
                            )
                        }
                    }
                }
            }
        }
    }
}
//
//@Preview
//@Composable
//fun ItemDetailScreenPreviewButItsEmptyLol(
//) {
//    MaterialTheme {
//        ItemDetailScreen(
//            state = ItemDetailScreenState.EMPTY,
//            viewModel = ItemDetailScreenViewModel(
//                networkRepository = NetworkRepository(
//                    MockUniversalisApi(), MockXIVApi()
//                ),
//                serverRepository = XIVServersRepository(
//                    MockUniversalisApi(),
//                    repositoryScope = TODO()
//                ),
//                detailSavedStateHandle = SavedStateHandle()
//            ),
//            navigateBack = {},
//        )
//    }
//}
//
//@Preview
//@Composable
//fun ItemDetailScreenPreviewButHasStuff(
//) {
//    MaterialTheme {
//        ItemDetailScreen(
//            state = ItemDetailScreenState(
//                marketItemDetail = ApiMarketItemDetail(),
//                itemDetail = ApiItemDetail(
//                    "TestItem",
//                    0,
//                    "",
//                    25,
//                    50,
//                    jobToEquip = mapOf(Pair("Name", "ACN BLM RDM SMN")),
//                    "This sure is an item that does stuff.",
//                    ApiItemKind("Arms")
//                ),
//                regionToSearch = "TestRegion",
//                cheapestTotalPrice = ApiPrices(
//                    5, 2000, 400, "Gilgamesh"
//                ),
//                cheapestUnitPrice = ApiPrices(
//                    99, 9900, 100, "Sargatanas"
//                ),
//                sortMethod = SortMethods.UNIT,
//                showHqOnly = false,
//                shouldShowDropdown = false
//            ),
//            viewModel = ItemDetailScreenViewModel(
//                networkRepository = NetworkRepository(
//                    MockUniversalisApi(), MockXIVApi()
//                ),
//                detailSavedStateHandle = SavedStateHandle()
//            ),
//            navigateBack = {},
//        )
//    }
//}