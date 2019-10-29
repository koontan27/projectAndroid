package com.example.calcalculated.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface profileDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(profile: profile)

    @Update
    fun update(profile: profile)

    @Query("DELETE FROM profile_item ")
    fun delete()

    @Query("SELECT * FROM profile_item ORDER BY sex ASC")
    fun getProfile(): LiveData<List<profile>>

}