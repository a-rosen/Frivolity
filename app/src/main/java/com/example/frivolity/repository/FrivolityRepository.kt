package com.example.frivolity.repository

import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiMarketItemDetail
import com.example.frivolity.network.models.universalisapi.ApiWorld
import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemList

interface FrivolityRepository {
    suspend fun getDataCenters(): List<ApiDataCenter>
    suspend fun getWorlds(): List<ApiWorld>
    suspend fun getMarketItemDetails(world: String, id: Int): ApiMarketItemDetail
    suspend fun getFullItemDetails(id: Int): ApiItemDetail
    suspend fun itemSearchByString(string: String): ApiItemList
}