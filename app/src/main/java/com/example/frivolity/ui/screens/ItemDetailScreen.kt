package com.example.frivolity.ui.screens

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.example.frivolity.navigation.NavigationDestination
import com.example.frivolity.network.MockUniversalisApi
import com.example.frivolity.network.MockXIVApi
import com.example.frivolity.network.models.universalisapi.asUiListingDetail
import com.example.frivolity.network.models.xivapi.asUiItemDetail
import com.example.frivolity.repository.NetworkRepository
import com.example.frivolity.ui.components.ItemDetailCard
import com.example.frivolity.ui.components.MarketItemListItem

object DetailsDestination : NavigationDestination {
    override val route = "ItemDetailScreen"
    const val worldNameArg = "worldName"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$worldNameArg}/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ItemDetailScreen(
    viewModel: ItemDetailScreenViewModel,
    navigateBack: () -> Unit,
) {
    val itemDetailScreenState by viewModel.screenStateFlow.collectAsState(
        initial = ItemDetailScreenState.EMPTY
    )
    val item = itemDetailScreenState.marketItemDetail
    val itemDetail = itemDetailScreenState.itemDetail

    val listings = item.listings.map {
        it.asUiListingDetail()
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
                        text = "Item Detail",
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                },
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            ItemDetailCard(
                itemDetail = itemDetail.asUiItemDetail(),
            )
            Text(text = "World Name: ${item.worldName}")
            Text(text = "Item Name: ${itemDetail.name}")
            LazyColumn {
                items(listings) {
                    MarketItemListItem(item = it)
                }
            }
        }
    }
}

@Preview
@Composable
fun ItemDetailScreenPreview(
) {
    ItemDetailScreen(
        ItemDetailScreenViewModel(
            repository = NetworkRepository(
                universalisApi = MockUniversalisApi(),
                xivApi = MockXIVApi()
            ),
            detailSavedStateHandle = SavedStateHandle()
        ),
        navigateBack = {}
    )

}