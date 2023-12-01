package com.example.frivolity.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ServerInfoDao {
    @Query("SELECT dcName FROM server_info")
    fun getSelectedDc() : String

    @Query("SELECT worldName FROM server_info")
    fun getSelectedWorld() : String

    @Query("UPDATE server_info SET dcName = :newDcName")
    fun setSelectedDc(newDcName: String)

    @Query("UPDATE server_info SET worldName = :newWorldName")
    fun setSelectedWorld(newWorldName: String)
}