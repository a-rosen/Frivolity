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

class DataStoreStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val repositoryScope: CoroutineScope,
) {
    private val storedSelectedDc = stringPreferencesKey("dcName")
    private val storedSelectedWorld = stringPreferencesKey("worldName")

    private val storedDcList = stringPreferencesKey("dcList")
    private val storedWorldsList = stringPreferencesKey("worldsList")
    private val storedServerList = stringPreferencesKey("serverList")

    val storedDcListJsonFlow: Flow<String> = dataStore
        .data
        .map { preferences ->
            preferences[storedDcList] ?: ""
        }
    val storedWorldsListJsonFlow: Flow<String> = dataStore
        .data
        .map { preferences ->
            preferences[storedWorldsList] ?: ""
        }

    val storedServerListJsonFlow: Flow<String> = dataStore
        .data
        .map { preferences ->
            preferences[storedServerList] ?: ""
        }


    val storedSelectedDcFlow: Flow<String> =
        dataStore
            .data
            .map { preferences ->
                preferences[storedSelectedDc] ?: ""
            }

    val storedSelectedWorldFlow: Flow<String> =
        dataStore
            .data
            .map { preferences ->
                preferences[storedSelectedWorld] ?: ""
            }



    fun saveDcList(dcListJson: String) {
        repositoryScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[storedDcList] = dcListJson
            }
        }
    }

    fun saveWorldsList(worldsListJson: String) {
        repositoryScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[storedWorldsList] = worldsListJson
            }
        }
    }

    fun saveServerList(serverListJson: String) {
        repositoryScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[storedServerList] = serverListJson
            }
        }
    }

    fun saveSelectedServer(selectedDcName: String, selectedWorldName: String) {
        repositoryScope.launch(Dispatchers.IO) {
            dataStore.edit { preferences ->
                preferences[storedSelectedDc] = selectedDcName
                preferences[storedSelectedWorld] = selectedWorldName
            }
        }
    }
}

// consider making this an implementation of an interface called storage,
// so that in future if you want to switch among storage methods you can easily
// do that.