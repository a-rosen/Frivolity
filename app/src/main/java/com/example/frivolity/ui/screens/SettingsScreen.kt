package com.example.frivolity.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.frivolity.ui.components.ListOfChoices

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel,
) {
    val state by viewModel.screenStateFlow.collectAsState()
    Column {
        Text(text = "Home Data Center:")
        Text(text = "Here's some raw data from the state! ${state.dcListRaw}")
        ListOfChoices(
            choices = listOf()
        ) {

        }
    }

}