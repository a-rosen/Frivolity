package com.example.frivolity.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
) {
    val mainScreenState by viewModel.screenStateFlow.collectAsState()
    Button(
        onClick = { viewModel.requestDataCenters() },
        content = { Text(text = "Do network things") }
    )
    LazyColumn() {
        items(mainScreenState.dataCentersList) {item ->
            Text(text = item.name)
            Text(text = item.region)
            Text(text = item.worlds.toString())

        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(
        viewModel = MainScreenViewModel(
            api = TODO()
        ),
    )
}