package com.blitzmachine.freetogamecom.io.remote

import com.blitzmachine.freetogamecom.io.classes.DetailedGame
import com.blitzmachine.freetogamecom.io.classes.Game
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HttpRouteController {

    @GET("games")
    fun getNewData(): Call<List<Game>>

    @GET("game")
    fun getGameDetails(
        @Query("id") id: Int): Call<DetailedGame>

}
