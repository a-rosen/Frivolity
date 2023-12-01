package com.example.frivolity.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "server_info")
data class ServerInfoEntity (
    @PrimaryKey val dcName: String,
    val worldName: String
)