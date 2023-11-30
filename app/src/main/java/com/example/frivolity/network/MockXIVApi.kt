package com.example.frivolity.network

import com.example.frivolity.network.models.xivapi.ApiWorldList

class MockXIVApi: XIVApi {
    override suspend fun getWorlds(): ApiWorldList {
        TODO("Not yet implemented")
    }
}