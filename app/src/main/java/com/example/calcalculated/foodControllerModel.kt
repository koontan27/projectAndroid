package com.example.calcalculated

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.calcalculated.db.*
import kotlinx.coroutines.launch

class foodControllerModel(application: Application) : AndroidViewModel(application) {
    private val repoOne: foodControllerRespository
    val allFoods: LiveData<List<listFood>>

    init {
        val foodListDao = listFoodDB.getDatabase(application.applicationContext).listFoodDAO()
        repoOne = foodControllerRespository(foodListDao)
        allFoods = repoOne.allFoods
    }

    fun insert(food: listFood) = viewModelScope.launch {
        repoOne.insert(food)
    }

    fun clear(item: String) = viewModelScope.launch {
        repoOne.clear(item)
    }
}

class profileControllerModel(application: Application) : AndroidViewModel(application) {
    private val repoTwo: profileControllerRespository
    val profileAlls: LiveData<List<profile>>

    init {
        val profileDAO = profileDB.getDatabase(application.applicationContext).profileDAO()
        repoTwo = profileControllerRespository(profileDAO)
        profileAlls = repoTwo.showProfile
    }

    fun insert(word: profile) = viewModelScope.launch {
        repoTwo.insert(word)
    }

    fun update(word: profile) = viewModelScope.launch {
        repoTwo.insert(word)
    }

    fun clear() = viewModelScope.launch {
        repoTwo.clear()
    }
}

class listFoodSelectedControllerModel(application: Application) : AndroidViewModel(application) {
    private val repoThree: listFoodSelectedControllerRespository
    val listFoodAll: LiveData<List<listFoodSelected>>

    init {
        val foodListDao =
            listFoodSelectedDB.getDatabase(application.applicationContext).listFoodSelectedDAO()
        repoThree = listFoodSelectedControllerRespository(foodListDao)
        listFoodAll = repoThree.allListFoodSelected
    }

    fun insert(food: listFoodSelected) = viewModelScope.launch {
        repoThree.insert(food)
    }

    fun clear(item: String) = viewModelScope.launch {
        repoThree.deleteByName(item)
    }
}