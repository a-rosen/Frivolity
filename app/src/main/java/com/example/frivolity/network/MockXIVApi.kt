package com.example.frivolity.network

import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemList
import com.example.frivolity.network.models.xivapi.ApiWorldList

class MockXIVApi: XIVApi {
    override suspend fun getWorlds(): ApiWorldList {
        TODO("Not yet implemented")
    }

    override suspend fun getItemById(id: Int): ApiItemDetail {
        TODO("Not yet implemented")
    }

    override suspend fun itemSearchByString(string: String): ApiItemList {
        TODO("Not yet implemented")
    }
}