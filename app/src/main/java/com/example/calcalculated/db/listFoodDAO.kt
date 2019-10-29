package com.example.calcalculated.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface listFoodDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(food: listFood)

    @Update
    fun update(food: listFood)

    @Query("DELETE FROM list_all_food WHERE foodName = :foodNameSelected ")
    fun deleteByName(foodNameSelected: String)

    @Query("SELECT * FROM list_all_food ORDER BY foodName ASC")
    fun getAllFood(): LiveData<List<listFood>>

}

@Dao
interface listFoodSelectedDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(food: listFoodSelected)

    @Query("DELETE FROM list_selected_food WHERE foodName = :foodNameSelected ")
    fun deleteByName(foodNameSelected: String)

    @Query("SELECT * FROM list_selected_food ORDER BY foodName ASC")
    fun getAllFood(): LiveData<List<listFoodSelected>>

}