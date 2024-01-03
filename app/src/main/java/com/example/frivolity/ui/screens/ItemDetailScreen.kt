package com.example.frivolity.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.example.frivolity.navigation.NavigationDestination
import com.example.frivolity.network.MockUniversalisApi
import com.example.frivolity.network.MockXIVApi
import com.example.frivolity.network.models.universalisapi.ApiMarketItemDetail
import com.example.frivolity.network.models.universalisapi.ApiPrices
import com.example.frivolity.network.models.universalisapi.asUiListingDetail
import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemKind
import com.example.frivolity.network.models.xivapi.asUiItemDetail
import com.example.frivolity.repository.NetworkRepository
import com.example.frivolity.ui.components.ChipRow
import com.example.frivolity.ui.components.ItemDetailHeader
import com.example.frivolity.ui.components.ListingListItem
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
                title = {
                    Text(
                        text = "Listings Detail: ${item.worldName}",
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
            BottomAppBar {

            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
        ) {
            ItemDetailHeader(
                itemDetail = itemDetail.asUiItemDetail(),
                numberOfListings = item.listings.size.toString()
            )

            ChipRow(
                onTotalSortClick = { viewModel.sortByTotal() },
                onUnitSortClick = { viewModel.sortByUnit() },
                state = state
            )

            LazyColumn(
            ) {
                if (state.sortMethod == SortMethods.TOTAL) {
                    items(listingsByTotal) {
                        ListingListItem(
                            headlineText = "%,d".format(it.total),
                            supportingText = "%,d".format(it.quantity) + " x " + "%,d".format(it.pricePerUnit),
                            isHq = it.hq
                        )
                    }
                } else {
                    items(listingsByUnit) {
                        ListingListItem(
                            headlineText = "%,d".format(it.pricePerUnit),
                            supportingText = "%,d".format(it.quantity) + " for " + "%,d".format(it.total),
                            isHq = it.hq
                        )
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun ItemDetailScreenPreviewButItsEmptyLol(
) {
    MaterialTheme {
        ItemDetailScreen(
            state = ItemDetailScreenState.EMPTY,
            viewModel = ItemDetailScreenViewModel(
                repository = NetworkRepository(
                    MockUniversalisApi(), MockXIVApi()
                ),
                detailSavedStateHandle = SavedStateHandle()
            ),
            navigateBack = {}
        )
    }
}

@Preview
@Composable
fun ItemDetailScreenPreviewButHasStuff(
) {
    MaterialTheme {
        ItemDetailScreen(
            state = ItemDetailScreenState(
                marketItemDetail = ApiMarketItemDetail(),
                itemDetail = ApiItemDetail(
                    "TestItem",
                    0,
                    "",
                    25,
                    50,
                    jobToEquip = mapOf(Pair("Name", "ACN BLM RDM SMN")),
                    "This sure is an item that does stuff.",
                    ApiItemKind("Arms")
                ),
                regionToSearch = "TestRegion",
                cheapestPrice = ApiPrices(
                    5, 2000, "Gilgamesh"
                ),
                sortMethod = SortMethods.UNIT,
            ),
            viewModel = ItemDetailScreenViewModel(
                repository = NetworkRepository(
                    MockUniversalisApi(), MockXIVApi()
                ),
                detailSavedStateHandle = SavedStateHandle()
            ),
            navigateBack = {}
        )
    }
}