package com.example.calcalculated.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_all_food")
data class listFood(
    @PrimaryKey

    @ColumnInfo(name = "foodName")
    val foodName: String,

    @ColumnInfo(name = "kcal")
    var kcalOfFood: Int

)

@Entity(tableName = "list_selected_food")
data class listFoodSelected(
    @PrimaryKey

    @ColumnInfo(name = "foodName")
    val foodName: String,

    @ColumnInfo(name = "kcal")
    var kcalOfFood: Int

)