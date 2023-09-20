package com.blitzmachine.freetogamecom.io

import android.content.res.Resources
import android.util.Log
import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blitzmachine.freetogamecom.MainActivity
import com.blitzmachine.freetogamecom.R
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.io.remote.FreeToGameAPI
import com.blitzmachine.freetogamecom.io.remote.HttpRoutes
import com.blitzmachine.freetogamecom.utils.APIUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.toImmutableList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class Repository(private val api: FreeToGameAPI) {

    private val _listOfLiveGames: MutableLiveData<List<Game>> = MutableLiveData()
    val listOfLiveGames: LiveData<List<Game>> get() = _listOfLiveGames

    init {
        api.httpRoutes.getLiveGamesList("browser", null, null).enqueue(object : Callback<List<Game>> {
            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.isSuccessful) {
                    _listOfLiveGames.postValue(response.body())
                } else {
                    Log.e(APIUtils.apiLogcatTag, "Response was not successful. ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Log.e(APIUtils.apiLogcatTag, "onFailure: ${t.message}")
            }
        })
    }
}