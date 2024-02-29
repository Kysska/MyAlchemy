package com.example.myalchemy.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ElementDao {
    @Query("SELECT key_id FROM element")
    fun getAllKey() : List<Int>

    @Insert(entity = ElementDBEntity::class)
    suspend fun insert(element: ElementDBEntity)
}