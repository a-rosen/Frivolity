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
import com.example.frivolity.network.MockUniversalisApi
import com.example.frivolity.network.MockXIVApi
import com.example.frivolity.repository.NetworkRepository
import java.util.Date

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
) {
    val mainScreenState by viewModel.screenStateFlow.collectAsState()
    Column {
        Button(
            onClick = {
                viewModel.requestWorlds()
            },
            content = { Text(text = "Get worlds") }
        )
        LazyColumn() {
            items(mainScreenState.dataCentersList) { item ->
                Text(text = item.name)
                Text(text = item.region)
                Text(text = item.worlds.toString())
            }
        }

        val names = mainScreenState.worldsList.results.map { item ->
            item.name
        }

        LazyColumn() {
            items(names) { item ->
                if (item != null) {
                    Text(text = item)
                } else Text(text = "null")
            }
        }
    }

    LazyColumn() {
        items(mainScreenState.recentlyUpdatedList.items) { item ->
            Text(text = item.worldName)
            Text(text = item.itemID.toString())
            Text(text = Date(item.lastUploadTime).toString())
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        viewModel = MainScreenViewModel(
            repository = NetworkRepository(
                MockUniversalisApi(),
                MockXIVApi()
            )
        ),
    )
}