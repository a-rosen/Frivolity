package com.example.frivolity.ui.screens

import android.util.Log
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
import androidx.compose.ui.Modifier
import com.example.frivolity.ui.components.ItemListItem
import com.example.frivolity.ui.components.SearchBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    updateSearchBoxText: (String) -> Unit,
    submitSearch: (String) -> Unit,
    navigateToDetailScreen: (String, Int) -> Unit,
) {
    Log.d("Timelog", "Rendering Scaffold...")
    Scaffold(
        topBar = {
            Log.d("Timelog", "Rendering TopAppBar...")
            TopAppBar(
                title = {
                    Text(
                        text = "Welcome",
                        style = MaterialTheme.typography.titleMedium
                    )
                })
        }
    ) { innerPadding ->
        Log.d("Timelog", "Rendering Column...")
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Divider()

            Log.d("Timelog", "Rendering SearchBox...")

            SearchBox(
                inputTextValue = state.searchBoxText,
                onValueChange = { newText ->
                    Log.d("Timelog", "Rendering new text: $newText")
                    updateSearchBoxText(newText)
                    submitSearch(newText)
                }
            )

            Log.d("Timelog", "Rendering LazyColumn...")
            LazyColumn {
                items(state.searchResults) {
                    ItemListItem(
                        itemName = it.name,
                        iconUrl = "https://xivapi.com/${it.icon}",
                        onClick = {
                            if (state.selectedWorld != null) {
                                navigateToDetailScreen(
                                    state.selectedWorld.name,
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