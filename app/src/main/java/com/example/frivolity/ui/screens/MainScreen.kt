package com.example.frivolity.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.frivolity.network.MockApi

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
) {
    val mainScreenState by viewModel.screenStateFlow.collectAsState()
    Column {
        Button(
            onClick = { viewModel.requestDataCenters() },
            content = { Text(text = "Get data centers") }
        )
        Button(
            onClick = {
                viewModel.requestRecentlyUpdated(
                    "Faerie", "North-America"
                )
            },
            content = { Text(text = "Get recently updated") }
        )
        LazyColumn() {
            items(mainScreenState.dataCentersList) { item ->
                Text(text = item.name)
                Text(text = item.region)
                Text(text = item.worlds.toString())
            }
        }

        LazyColumn() {
            items(mainScreenState.recentlyUpdatedList.items) { item ->
                Text(text = item.worldName)
                Text(text = item.itemID.toString())
                Text(text = item.worldID.toString())
                Text(text = item.lastUploadTime.toString())
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        viewModel = MainScreenViewModel(
            api = MockApi()
        ),
    )
}