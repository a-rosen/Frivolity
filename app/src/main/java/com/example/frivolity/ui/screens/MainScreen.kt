package com.example.frivolity.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.frivolity.ui.components.ItemListItem
import com.example.frivolity.ui.components.SearchBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    navigateToDetailScreen: (String, Int) -> Unit,
) {
    val mainScreenState by viewModel.screenStateFlow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Welcome",
                        style = MaterialTheme.typography.titleMedium
                    )
                })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Divider()

            SearchBox(
                inputTextValue = mainScreenState.searchBoxText,
                onValueChange = {newText ->
                    viewModel.updateSearchBoxText(newText)
                    viewModel.submitSearch(newText)
                }
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
    }
}