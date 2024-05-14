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

    val currentDcWorldsList = state.currentLogicalDc?.worlds

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
                    if (currentDcWorldsList != null) {
                        ButtonWithDropdown(
                            icon = R.drawable.ic_world,
                            iconDescription = "world",
                            menuItems = currentDcWorldsList
                                .map { it.name },
                            onItemClicked = { changeServer(it, item.itemID) },
                            onIconClicked = { viewModel.toggleWorldList() },
                            expanded = state.shouldShowWorldList
                        )
                    }
                },
                title = {
                    Text(
                        text = "Listings Detail:${state.currentLogicalDc?.name} ${item.worldName}",
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
                    itemDetail = itemDetail.asUiItemDetail(),
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