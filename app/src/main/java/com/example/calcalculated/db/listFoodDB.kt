package com.example.calcalculated.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(listFood::class), version = 1, exportSchema = false)
abstract class listFoodDB : RoomDatabase() {

    abstract fun listFoodDAO(): listFoodDAO

    companion object {
        @Volatile
        private var INSTANCE: listFoodDB? = null

        fun getDatabase(
            context: Context
        ): listFoodDB {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    listFoodDB::class.java,
                    "food_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Database(entities = arrayOf(listFoodSelected::class), version = 1, exportSchema = false)
abstract class listFoodSelectedDB : RoomDatabase() {

    abstract fun listFoodSelectedDAO(): listFoodSelectedDAO

    companion object {
        @Volatile
        private var INSTANCE: listFoodSelectedDB? = null

        fun getDatabase(
            context: Context
        ): listFoodSelectedDB {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    listFoodSelectedDB::class.java,
                    "list_food_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}