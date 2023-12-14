package com.example.frivolity.repository

import com.example.frivolity.network.UniversalisApi
import com.example.frivolity.network.XIVApi
import com.example.frivolity.network.models.universalisapi.ApiDataCenter
import com.example.frivolity.network.models.universalisapi.ApiMarketItemDetail
import com.example.frivolity.network.models.universalisapi.ApiWorld
import com.example.frivolity.network.models.xivapi.ApiItemDetail
import com.example.frivolity.network.models.xivapi.ApiItemList
import com.example.frivolity.network.models.xivapi.asItemDetailDataRecord
import com.example.frivolity.repository.models.ItemDetailDataRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val universalisApi: UniversalisApi,
    private val xivApi: XIVApi,
) : FrivolityRepository {
    val _internalDataFlow = MutableStateFlow<ItemDetailDataRecord>(
        value = ItemDetailDataRecord(
            "", "", 0, 0
        )
    )
    override val dataFlow = _internalDataFlow.asStateFlow()

    override suspend fun getDataCenters(): List<ApiDataCenter> {
        return universalisApi.getDataCenters()
    }

    override suspend fun getWorlds(): List<ApiWorld> {
        return universalisApi.getWorlds()
    }

    override suspend fun getMarketItemDetails(world: String, id: Int): ApiMarketItemDetail {
        return universalisApi.getItemDetails(world, id)
    }

    override suspend fun getFullItemDetails(id: Int): ApiItemDetail {
        return xivApi.getItemById(id)
    }

    override suspend fun getItemLevelById(id: Int) {
        _internalDataFlow.update {
            xivApi.getItemById(id).asItemDetailDataRecord()
        }
    }

    override suspend fun itemSearchByString(string: String): ApiItemList {
        return xivApi.itemSearchByString(string)
    }

}