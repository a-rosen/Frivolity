package com.example.frivolity.network

import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemList
import com.example.frivolity.network.models.xivapi.ApiWorldList
import retrofit2.http.Query

class MockXIVApi: XIVApi {
    override suspend fun getWorlds(): ApiWorldList {
        TODO("Not yet implemented")
    }

    override suspend fun getItemById(id: Int): ApiItemDetail {
        TODO("Not yet implemented")
    }

    override suspend fun getItemLevelById(id: Int, columns: String): ApiItemDetail {
        TODO("Not yet implemented")
    }

    override suspend fun itemSearchByString(
        @Query(value = "string") string: String,
        @Query(value = "filters") filters: String
    ): ApiItemList {
        TODO("Not yet implemented")
    }
}