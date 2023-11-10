package com.blitzmachine.freetogamecom.io

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blitzmachine.freetogamecom.io.classes.DetailedGame
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.io.classes.Genre
import com.blitzmachine.freetogamecom.io.classes.Platform
import com.blitzmachine.freetogamecom.io.local.GameDatabase
import com.blitzmachine.freetogamecom.io.remote.FreeToGameAPI
import com.blitzmachine.freetogamecom.utils.APIUtils
import com.blitzmachine.freetogamecom.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class Repository(private val api: FreeToGameAPI, private val database: GameDatabase, private val context: Context) {

    private val _listOfNewGames: MutableLiveData<List<Game>> = MutableLiveData()
    val listOfNewGame: LiveData<List<Game>> get() = _listOfNewGames

    private val _detailsOfGame: MutableLiveData<DetailedGame> = MutableLiveData()
    val detailsOfGame: LiveData<DetailedGame> get() = _detailsOfGame

    private val _filteredListOfGames: MutableLiveData<List<Game>> = MutableLiveData()
    val filteredListOfGames: LiveData<List<Game>> get() = _filteredListOfGames

    val cachedGames: LiveData<List<Game>> = database.databaseDao().getGames()

    init {
        when (Utils.isOnline(context)) {
            true -> {
                Log.d("Repository", "isOnline - Fetching data for equality check with stored data")
                fetchNewData()
            }
            false -> Log.d("Repository","isOffline - Loading stored data ...")
        }
    }

    suspend fun cacheGame(game: Game) {
        database.databaseDao().upsertGame(game)
    }

    suspend fun cacheGames(games: List<Game>) {
        database.databaseDao().insertGames(games)
    }

    suspend fun getFilteredCachedGames(platform: Set<String>?, genre: Set<String>?) {
        var test = emptyList<Game>()
        try {
        test = database.databaseDao().getFilteredGames(platform, genre)
        } catch (ex: Exception) {
            Log.e("Repository", "Filter Request failed! ${ex.message}")
            Log.e("Repository", test.toString())
        }
        _filteredListOfGames.postValue(test)
    }

    fun fetchNewData() {
        try {
            api.httpRoutes.getNewData().enqueue(object : Callback<List<Game>> {
                    override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                        // Returns true if the code is in [200..300), which means the request was successfully received, understood, and accepted
                        if (response.isSuccessful) {
                            _listOfNewGames.postValue(response.body())
                        } else {
                            Log.e(APIUtils.apiLogcatTag, "LiveGamesRequest failed. Response Code: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                        Log.e(APIUtils.apiLogcatTag, "LiveGamesRequest failed: ${t.message}")
                    }
                })
        } catch (ex: Exception) {
            Log.e(APIUtils.apiLogcatTag,"LiveGamesRequest Exception: ${ex.message}")
        }
    }

    fun getDetailsOfGame(id: Int) {
        try {
            api.httpRoutes.getGameDetails(id).enqueue(object : Callback<DetailedGame> {
                override fun onResponse(call: Call<DetailedGame>, response: Response<DetailedGame>) {
                    if (response.isSuccessful) {
                        _detailsOfGame.postValue(response.body())
                    } else {
                        Log.e(APIUtils.apiLogcatTag, "GameDetailsRequest failed. Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<DetailedGame>, t: Throwable) {
                    Log.e(APIUtils.apiLogcatTag, "GameDetailsRequest failed: ${t.message}")
                }
            })
        } catch (ex: Exception) {
            Log.e(APIUtils.apiLogcatTag, "GameDetailRequest Exception: ${ex.message}")
        }
    }
}