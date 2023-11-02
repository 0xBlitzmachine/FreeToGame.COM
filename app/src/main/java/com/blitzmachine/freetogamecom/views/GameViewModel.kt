package com.blitzmachine.freetogamecom.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.blitzmachine.freetogamecom.io.Repository
import com.blitzmachine.freetogamecom.io.classes.DetailedGame
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.io.local.GameDatabase
import com.blitzmachine.freetogamecom.io.remote.FreeToGameAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val database = GameDatabase.getDatabase(application)
    private val repository = Repository(FreeToGameAPI, database)

    val listOfNewGame: LiveData<List<Game>> = repository.listOfNewGame
    val detailsOfGame: LiveData<DetailedGame> = repository.detailsOfGame
    val cachedGames: LiveData<List<Game>> = repository.cachedGames

    fun cacheGame(game: Game)  {
        viewModelScope.launch(Dispatchers.IO) {
            repository.cacheGame(game)
        }
    }

    fun cacheGames(games: List<Game>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.cacheGames(games)
        }
    }

    fun fetchNewData(platform: String? = null, category: String? = null, sortBy: String? = null) {
        repository.fetchNewData(platform, category, sortBy)
    }

    fun fetchNewFilteredData(tag: String? = null, platform: String? = null) {
        repository.fetchNewFilteredData(tag, platform)
    }

    fun getDetailsOfGame(id: Int) {
        repository.getDetailsOfGame(id)
    }

}