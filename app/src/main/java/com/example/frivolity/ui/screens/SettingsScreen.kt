package com.example.frivolity.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.frivolity.ui.components.ListOfChoices

@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel,
) {
    Column {
        Text(text = "Home Data Center:")
        ListOfChoices(
            choices = listOf()
        ) {

        }
    }

}