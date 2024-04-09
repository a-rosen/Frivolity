package com.example.frivolity.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val repositoryScope: CoroutineScope
) {
    private val storedDcName = stringPreferencesKey("dcName")
    private val storedWorldName = stringPreferencesKey("worldName")

    private val storedDcList = stringPreferencesKey("dcList")
    private val storedWorldsList = stringPreferencesKey("worldsList")

    val storedDcFlow: Flow<String> = dataStore
        .data
        .map { preferences -> preferences[storedDcName] ?: "" }

    val storedWorldFlow: Flow<String> = dataStore.data
        .map { data ->
            data[storedWorldName] ?: ""
        }

    val storedDcListFlow: Flow<String> = dataStore
        .data
        .map { preferences -> preferences[storedDcList] ?: "" }

    fun saveDcList(rawDcList: String) {
        repositoryScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[storedDcList] = rawDcList
            }
        }
    }

    fun saveWorldsList() {
        TODO()
    }

    fun deserializeStoredDcList() {
        repositoryScope.launch(Dispatchers.IO) {
            storedDcListFlow.collect {dcListJsonString ->
                TODO()

            }
        }

    }

    fun saveSelectedServer(selectedDcName: String, selectedWorldName: String) {
        repositoryScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[storedDcName] = selectedDcName
                preferences[storedWorldName] = selectedWorldName
            }
        }
    }
}