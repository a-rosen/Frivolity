package com.example.frivolity.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.frivolity.ui.components.InputWithButton
import com.example.frivolity.ui.components.ItemListItem

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    navigateToDetailScreen: (String, Int) -> Unit,
) {
    val mainScreenState by viewModel.screenStateFlow.collectAsState()


    InputWithButton(
        onButtonClick = { viewModel.submitSearch(mainScreenState.searchBoxText) },
        onTextChange = { viewModel.updateSearchBoxText(it) },
        displayedText = mainScreenState.searchBoxText
    )

    LazyColumn {
        items(mainScreenState.searchResults) {
            ItemListItem(
                itemName = it.name,
                iconUrl = "https://xivapi.com/${it.icon}",
                itemLevel = it.iLevel,
                onClick = {
                    if (mainScreenState.selectedWorld != null) {
                        navigateToDetailScreen(
                            mainScreenState.selectedWorld!!.name,
                            it.id
                        )
                    }
                }
            )
        }
    }
}