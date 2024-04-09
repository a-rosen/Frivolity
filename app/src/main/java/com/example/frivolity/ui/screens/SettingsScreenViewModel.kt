package com.example.frivolity.ui.screens

import androidx.lifecycle.ViewModel
import com.example.frivolity.repository.XIVServersRepository
import javax.inject.Inject

class SettingsScreenViewModel @Inject constructor(
    private val serverRepository: XIVServersRepository,
) : ViewModel() {
    fun getListOfDcs() {

    }

}
