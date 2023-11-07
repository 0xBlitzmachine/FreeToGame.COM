package com.blitzmachine.freetogamecom.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blitzmachine.freetogamecom.io.Repository
import com.blitzmachine.freetogamecom.io.classes.DetailedGame
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.io.classes.Genre
import com.blitzmachine.freetogamecom.io.classes.Platform
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

    private val _selectedPlatform: MutableLiveData<Platform> = MutableLiveData(Platform.ALL)
    val selectedPlatform: LiveData<Platform?> get() = _selectedPlatform

    private val _selectedGenre: MutableLiveData<List<Genre>?> = MutableLiveData(null)
    val selectedGenre: LiveData<List<Genre>?> get() = _selectedGenre

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

    fun fetchNewData() {
        repository.fetchNewData()
    }

    fun getDetailsOfGame(id: Int) {
        repository.getDetailsOfGame(id)
    }
}