package com.example.calcalculated

import androidx.lifecycle.LiveData
import com.example.calcalculated.db.*

class foodControllerRespository(private val foodDAO: listFoodDAO) {
    val allFoods: LiveData<List<listFood>> = foodDAO.getAllFood()

    fun insert(food: listFood) {
        foodDAO.insert(food)
    }
    fun clear(item: String) {
        foodDAO.deleteByName(item)
    }
}


class profileControllerRespository(private val profileDAO: profileDAO) {
    val showProfile: LiveData<List<profile>> = profileDAO.getProfile()

    fun insert(p: profile) {
        profileDAO.insert(p)
    }

    fun update(p: profile) {
        profileDAO.update(p)
    }

    fun clear() {
        profileDAO.delete()
    }
}

class listFoodSelectedControllerRespository(private val listFoodSelectedDAO: listFoodSelectedDAO) {
    val allListFoodSelected: LiveData<List<listFoodSelected>> = listFoodSelectedDAO.getAllFood()

    fun insert(list: listFoodSelected) {
        listFoodSelectedDAO.insert(list)
    }

    fun deleteByName(item: String) {
        listFoodSelectedDAO.deleteByName(item)
    }
}