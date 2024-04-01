package com.example.frivolity.repository

import com.example.frivolity.network.models.universalisapi.ApiMarketItemDetail
import com.example.frivolity.network.models.universalisapi.ApiPricesList
import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemList

interface FrivolityRepository {
    suspend fun getMarketItemDetails(world: String, id: Int): ApiMarketItemDetail
    suspend fun getFullItemDetails(id: Int): ApiItemDetail
    suspend fun itemSearchByString(string: String): ApiItemList
    suspend fun getMarketItemPrices(region: String, id: Int): ApiPricesList
}