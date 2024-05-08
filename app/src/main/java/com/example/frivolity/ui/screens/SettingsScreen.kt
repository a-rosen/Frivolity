package com.example.frivolity.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.frivolity.ui.components.ListOfChoices

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel,
    navigateToMainScreen: () -> Unit,
) {
    val state by viewModel.screenStateFlow.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (state.dcHasBeenSelected) {
                        IconButton(
                            onClick = { viewModel.toggleSelectingDcView() },
                            content = {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "back button"
                                )
                            }
                        )
                    }
                },
                title = {
                    if (state.dcHasBeenSelected) {
                        Text(
                            text = "Select Home World:",
                            style = MaterialTheme.typography.titleMedium
                        )

                    } else {
                        Text(
                            text = "Select Home Data Center:",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Divider()
            AnimatedContent(
                targetState = state.dcHasBeenSelected,
                label = "selection",
                transitionSpec = {
                    if (state.dcHasBeenSelected) {
                        (slideInHorizontally { fullWidth -> fullWidth } + fadeIn())
                            .togetherWith(
                                slideOutHorizontally { -0 } + fadeOut())
                    } else {
                        (slideInHorizontally() + fadeIn())
                            .togetherWith(
                                slideOutHorizontally() + fadeOut()
                            )
                    }
                }
            ) { dcHasBeenSelected ->
                when (dcHasBeenSelected) {
                    false -> {
                        ListOfChoices(
                            choices = state.logicalDcsList
                                .filter { server ->
                                    val firstWorldOnList = server.worlds.firstOrNull()
                                    if (firstWorldOnList != null) {
                                        firstWorldOnList.id < 1000
                                    } else true
                                }
                                .map { server ->
                                    server.name
                                }
                                .sorted(),
                            onListItemClick = { viewModel.selectDc(it) }
                        )
                    }

                    true -> {
                        Column {
                            val selectedApiDcFromStorage = state.logicalDcsList
                                .firstOrNull() { logicalDc ->
                                    logicalDc.name == state.selectedDcName
                                }

                            selectedApiDcFromStorage?.worlds?.let { apiWorldList ->
                                ListOfChoices(
                                    choices = apiWorldList
                                        .map { world -> world.name }
                                        .sorted(),
                                    onListItemClick = { viewModel.selectWorld(it) }
                                )
                            }

                            Button(onClick = {
                                viewModel.saveSelectedServer(
                                    state.selectedDcName,
                                    state.selectedWorldName
                                )
                                navigateToMainScreen()
                            }) {
                                Text(text = "Next")
                            }
                        }
                    }
                }
            }
        }
    }
}
