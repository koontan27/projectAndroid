package com.example.calcalculated.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_item")

data class profile(
    @PrimaryKey

    @ColumnInfo(name = "sex")
    val sex: String,

    @ColumnInfo(name = "age")
    var age: Int,

    @ColumnInfo(name = "weight")
    var weight: Double,

    @ColumnInfo(name = "height")
    var height: Double,

    @ColumnInfo(name = "activitySelected")
    var activitySelected: String,

    @ColumnInfo(name = "calBmi")
    var calBmi: Double,

    @ColumnInfo(name = "calWeight")
    var calWeight: String,

    @ColumnInfo(name = "calTotal")
    var calTotal: Int

)