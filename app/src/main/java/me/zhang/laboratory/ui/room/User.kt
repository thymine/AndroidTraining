package me.zhang.laboratory.ui.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "avatar") var avatar: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "maiden_name") val maiden_name: String?,
)
