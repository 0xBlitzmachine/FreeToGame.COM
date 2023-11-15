package com.blitzmachine.freetogamecom.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.blitzmachine.freetogamecom.MainActivity
import com.blitzmachine.freetogamecom.io.Repository
import com.blitzmachine.freetogamecom.io.classes.DetailedGame
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.io.local.GameDatabase
import com.blitzmachine.freetogamecom.io.remote.FreeToGameAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val database = GameDatabase.getDatabase(application)
    private val repository = Repository(FreeToGameAPI, database, application)

    val listOfNewGame: LiveData<List<Game>> = repository.listOfNewGame
    val detailsOfGame: LiveData<DetailedGame> = repository.detailsOfGame
    val cachedGames: LiveData<List<Game>> = repository.cachedGames
    val filteredCachedGames: LiveData<List<Game>> = repository.filteredListOfGames

    val displayCriticalError: LiveData<Boolean> = repository.displayCriticalError
    val criticalTitle: LiveData<String> = repository.criticalErrorTitle
    val criticalMessage: LiveData<String> = repository.criticalErrorMessage

    var isFiltered = false

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

    fun getFilteredCachedGames(platform: Set<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFilteredGames(platform)
        }
    }

    fun getFilteredCachedGames(platform: Set<String>, genre: Set<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFilteredGames(platform, genre)
        }
    }

    fun fetchNewData() {
        repository.fetchNewData()
    }

    fun getDetailsOfGame(id: Int) {
        repository.getDetailsOfGame(id)
    }
}