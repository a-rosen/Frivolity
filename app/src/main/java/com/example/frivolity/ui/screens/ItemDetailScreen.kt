package com.example.frivolity.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import com.example.frivolity.navigation.NavigationDestination
import com.example.frivolity.network.MockUniversalisApi
import com.example.frivolity.network.MockXIVApi
import com.example.frivolity.repository.NetworkRepository

object DetailsDestination : NavigationDestination {
    override val route = "ItemDetailScreen"
    const val worldNameArg = "worldName"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$worldNameArg}/{$itemIdArg}"
}

@Composable
fun ItemDetailScreen(
    viewModel: ItemDetailScreenViewModel,
) {
    val itemDetailScreenState by viewModel.screenStateFlow.collectAsState(
        initial = ItemDetailScreenState.EMPTY
    )
    val item = itemDetailScreenState.item

    Column {
        Text(text = "Item ID: ${item.itemID}")
        Text(text = "World Name: ${item.worldName}")
        LazyColumn(content =
        {
            this.item {
                Text(text = "Listings: ${item.listings}")
            }
        }
        )
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
        )
    )

}