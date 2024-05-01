package com.example.frivolity.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
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
        if (state.selectedDcName == "") {
            Text(text = "Home Data Center:")
            ListOfChoices(
                choices = state.logicalDcsList
                    .filter { server ->
                        val firstWorldOnList = server.worlds.firstOrNull()
                        if (firstWorldOnList != null) {
                            firstWorldOnList.id < 1000
                        } else return
                    }
                    .map { server ->
                        server.name
                    },
                onListItemClick = { viewModel.selectDc(it) }
            )
        } else if (state.selectedWorldName == "") {
            val selectedApiDcFromStorage = state.logicalDcsList
                .firstOrNull() { logicalDc ->
                    logicalDc.name == state.selectedDcName
                }
            Text(text = "Home World:")
            selectedApiDcFromStorage?.worlds?.let { apiWorldList ->
                ListOfChoices(
                    choices = apiWorldList
                        .map { world -> world.name },
                    onListItemClick = { viewModel.selectWorld(it) }
                )
            }

        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Next")
        }
    }
}