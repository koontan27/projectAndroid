package com.example.calcalculated.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(profile::class), version = 1, exportSchema = false)
abstract class profileDB : RoomDatabase() {

    abstract fun profileDAO(): profileDAO

    companion object {
        @Volatile
        private var INSTANCE: profileDB? = null

        fun getDatabase(
            context: Context
        ): profileDB {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    profileDB::class.java,
                    "profile_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}