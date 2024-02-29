package com.example.myalchemy.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [ElementDBEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun getElementDao(): ElementDao
    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "element_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}