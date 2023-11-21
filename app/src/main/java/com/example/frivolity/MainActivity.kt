package com.example.frivolity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.frivolity.ui.screens.AppContainerScreen
import com.example.frivolity.ui.theme.FrivolityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrivolityTheme {
                AppContainerScreen()
            }
        }
    }
}