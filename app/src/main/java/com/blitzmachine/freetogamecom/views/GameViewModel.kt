package com.blitzmachine.freetogamecom.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.blitzmachine.freetogamecom.io.Repository
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.io.classes.Games
import com.blitzmachine.freetogamecom.io.remote.FreeToGameAPI

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = Repository(FreeToGameAPI)

    val allLiveGames: LiveData<List<Games>> = repository.listOfLiveGames
    val detailsOfSingleGame: LiveData<Game> = repository.detailsOfSingleGame

    fun getAllLiveGames() {
        repository.getListOfLiveGames()
    }

    fun getDetailsOfGame(id: Int) {
        repository.getDetailsOfGame(id)
    }
}