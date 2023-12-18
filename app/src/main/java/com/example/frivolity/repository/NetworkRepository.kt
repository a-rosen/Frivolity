package com.example.frivolity.repository

import com.example.frivolity.network.UniversalisApi
import com.example.frivolity.network.XIVApi
import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiMarketItemDetail
import com.example.frivolity.network.models.universalisapi.ApiPricesList
import com.example.frivolity.network.models.universalisapi.ApiWorld
import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemList
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val universalisApi: UniversalisApi,
    private val xivApi: XIVApi,
) : FrivolityRepository {

    override suspend fun getDataCenters(): List<ApiDataCenter> {
        return universalisApi.getDataCenters()
    }

    override suspend fun getWorlds(): List<ApiWorld> {
        return universalisApi.getWorlds()
    }

    override suspend fun getMarketItemDetails(world: String, id: Int): ApiMarketItemDetail {
        return universalisApi.getItemDetails(world, id)
    }

    override suspend fun getMarketItemPrices(region: String, id: Int): ApiPricesList {
        return universalisApi.getItemPrices(region, id, "listings.quantity,listings.total,listings.worldName")
    }

    override suspend fun getFullItemDetails(id: Int): ApiItemDetail {
        return xivApi.getItemById(id)
    }

    override suspend fun itemSearchByString(string: String): ApiItemList {
        return xivApi.itemSearchByString(string)
    }

}