package com.example.frivolity.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.frivolity.database.dao.ServerInfoDao
import com.example.frivolity.database.models.ServerInfoEntity

@Database(entities = [ServerInfoEntity::class], version = 1)
abstract class FrivolityDatabase : RoomDatabase() {
    abstract fun serverInfoDao(): ServerInfoDao
}