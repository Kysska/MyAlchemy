package com.example.myalchemy.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "element")
data class ElementDBEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "key_id")val keyId : Int
)
