package com.blitzmachine.freetogamecom.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.blitzmachine.freetogamecom.io.Repository
import com.blitzmachine.freetogamecom.io.classes.DetailedGame
import com.blitzmachine.freetogamecom.io.classes.Games
import com.blitzmachine.freetogamecom.io.local.GameDatabase
import com.blitzmachine.freetogamecom.io.remote.FreeToGameAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val database = GameDatabase.getDatabase(application)
    private val repository = Repository(FreeToGameAPI, database)

    val listOfLiveGames: LiveData<List<Games>> = repository.listOfLiveGames
    val detailsOfSingleGame: LiveData<DetailedGame> = repository.detailsOfSingleGame
    val allCachedGames: LiveData<List<Games>> = repository.cachedGames

    fun cacheGame(game: Games)  {
        viewModelScope.launch(Dispatchers.IO) {
            repository.cacheGame(game)
        }
    }

    fun getAllLiveGames(platform: String? = null, category: String? = null, sortBy: String? = null) {
        repository.getListOfLiveGames(platform, category, sortBy)
    }

    fun getDetailsOfGame(id: Int) {
        repository.getDetailsOfGame(id)
    }

    fun getFilteredGameList(tag: String? = null, platform: String? = null) {
        repository.getFilteredGameList(tag, platform)
    }
}