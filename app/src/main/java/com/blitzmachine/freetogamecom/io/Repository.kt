package com.blitzmachine.freetogamecom.io

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.ImageResult
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.io.classes.Games
import com.blitzmachine.freetogamecom.io.local.DatabaseDao
import com.blitzmachine.freetogamecom.io.local.GameDatabase
import com.blitzmachine.freetogamecom.io.remote.FreeToGameAPI
import com.blitzmachine.freetogamecom.utils.APIUtils
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class Repository(private val api: FreeToGameAPI, private val database: GameDatabase) {

    private val _listOfLiveGames: MutableLiveData<List<Games>> = MutableLiveData()
    val listOfLiveGames: LiveData<List<Games>> get() = _listOfLiveGames

    private val _detailsOfSingleGame: MutableLiveData<Game> = MutableLiveData()
    val detailsOfSingleGame: LiveData<Game> get() = _detailsOfSingleGame

    init {
        getListOfLiveGames()
    }

    fun getListOfLiveGames(platform: String? = null, category: String? = null, sortBy: String? = null) {
        try {
            api.httpRoutes.getLiveGamesList(platform, category, sortBy).enqueue(object : Callback<List<Games>> {
                    override fun onResponse(call: Call<List<Games>>, response: Response<List<Games>>) {
                        if (response.isSuccessful) {
                            _listOfLiveGames.postValue(response.body())
                        } else {
                            Log.e(APIUtils.apiLogcatTag, "LiveGamesRequest failed. Response Code: ${response.code()}")
                        }
                    }


                    override fun onFailure(call: Call<List<Games>>, t: Throwable) {
                        Log.e(APIUtils.apiLogcatTag, "LiveGamesRequest failed: ${t.message}")
                    }
                })
        } catch (ex: Exception) {
            Log.e(APIUtils.apiLogcatTag,"LiveGamesRequest Exception: ${ex.message}")
        }
    }

    fun getDetailsOfGame(id: Int) {
        try {
            api.httpRoutes.getGameDetails(id).enqueue(object : Callback<Game> {
                override fun onResponse(call: Call<Game>, response: Response<Game>) {
                    if (response.isSuccessful) {
                        _detailsOfSingleGame.postValue(response.body())
                    } else {
                        Log.e(APIUtils.apiLogcatTag, "GameDetailsRequest failed. Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Game>, t: Throwable) {
                    Log.e(APIUtils.apiLogcatTag, "GameDetailsRequest failed: ${t.message}")
                }
            })
        } catch (ex: Exception) {
            Log.e(APIUtils.apiLogcatTag, "GameDetailRequest Exception: ${ex.message}")
        }
    }

    fun getFilteredGameList(tag: String? = null, platform: String? = null) {
        try {
            api.httpRoutes.getFilteredGameList(tag, platform).enqueue(object : Callback<List<Games>> {
                override fun onResponse(call: Call<List<Games>>, response: Response<List<Games>>) {
                    if (response.isSuccessful) {
                        _listOfLiveGames.postValue(response.body())
                    } else {
                        Log.e(APIUtils.apiLogcatTag, "FilteredGameList failed. Response Code: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Games>>, t: Throwable) {
                    Log.e(APIUtils.apiLogcatTag, "FilteredGameList failed: ${t.message}")
                }

            })
        } catch (ex: Exception) {
            Log.e(APIUtils.apiLogcatTag, "FilteredGameList Exception: ${ex.message}")
        }
    }
}