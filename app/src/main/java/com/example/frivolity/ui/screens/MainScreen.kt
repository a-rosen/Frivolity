package com.example.frivolity.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.frivolity.network.MockUniversalisApi
import com.example.frivolity.network.MockXIVApi
import com.example.frivolity.repository.NetworkRepository
import com.example.frivolity.ui.components.ButtonWithDropdown

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel,
) {
    val mainScreenState by viewModel.screenStateFlow.collectAsState()
    val dataCenters = mainScreenState.dataCentersList.filter {
        it.worlds[0] < 1000
    }
        .map { it.name }

    Column {
        ButtonWithDropdown(
            toggleDropdown = {},
            menuItems = dataCenters
        )
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