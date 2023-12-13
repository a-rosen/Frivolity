package com.example.frivolity.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.frivolity.ui.components.ButtonWithDropdown
import com.example.frivolity.ui.components.InputWithButton
import com.example.frivolity.ui.components.ItemListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
    navigateToDetailScreen: (String, Int) -> Unit,
) {
    val mainScreenState by viewModel.screenStateFlow.collectAsState()
    val dataCenters = mainScreenState.dataCentersList.filter { it.worlds[0] < 1000 }
    val dataCentersNames = dataCenters.map { it.name }
    val selectedDcWorlds = mainScreenState.worldsList.filter {
        mainScreenState
            .selectedDC?.worlds?.contains(it.id) ?: false
    }
    val selectedDcWorldsNames = selectedDcWorlds.map { it.name }

    Column {
        ButtonWithDropdown(
            menuItems = dataCentersNames,
            displayText = "Select Data Center",
            onItemClicked = { viewModel.selectDataCenter(it) }
        )

        Text(text = mainScreenState.selectedDC?.name ?: "")

        ButtonWithDropdown(
            menuItems = selectedDcWorldsNames,
            displayText = "Select World",
            onItemClicked = { viewModel.selectWorld(it) }
        )

        Text(text = mainScreenState.selectedWorld?.name ?: "")

        Button(
            onClick = {
                viewModel.saveSelectedServer(
                    mainScreenState.selectedDC?.name ?: "",
                    mainScreenState.selectedWorld?.name ?: ""
                )
            },
            content = { Text(text = "Save current selection") }
        )

        Button(
            onClick = {
                if (mainScreenState.selectedWorld != null) {
                    navigateToDetailScreen(
                        mainScreenState.selectedWorld!!.name,
                        23098
                    )
                }
            },
            content = { Text("Item Details") }
        )

        InputWithButton(
            onButtonClick = { viewModel.submitSearch(mainScreenState.searchBoxText) },
            onTextChange = { viewModel.updateSearchBoxText(it) },
            displayedText = mainScreenState.searchBoxText
        )

        LazyColumn {
            items(mainScreenState.searchResults.results) {
                ItemListItem(
                    itemName = it.name,
                    iconUrl = "https://xivapi.com/${it.icon}"
                )
            }
        }
    }
}