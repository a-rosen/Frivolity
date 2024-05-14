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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.frivolity.ui.components.InputWithButton
import com.example.frivolity.ui.components.ItemListItem

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

            InputWithButton(
                onButtonClick = { viewModel.submitSearch(mainScreenState.searchBoxText) },
                onTextChange = { viewModel.updateSearchBoxText(it) },
                displayedText = mainScreenState.searchBoxText,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(24.dp)
            )

            Divider()

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