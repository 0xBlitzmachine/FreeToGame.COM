package com.blitzmachine.freetogamecom.views

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.blitzmachine.freetogamecom.io.Repository
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.io.classes.Games
import com.blitzmachine.freetogamecom.io.remote.FreeToGameAPI
import java.lang.Exception

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(FreeToGameAPI)

    val allLiveGames: LiveData<List<Games>> = repository.listOfLiveGames
    val detailsOfSingleGame: LiveData<Game> = repository.detailsOfSingleGame

    fun getAllLiveGames() {
        repository.getListOfLiveGames()
    }

    fun getDetailsOfGame(id: Int) {
        try {
            repository.getDetailsOfGame(id)
        } catch (ex: Exception) {
            Log.e("ViewModel", "Failed to get Details of Game - ${ex.message}")
        }
    }
}