package com.blitzmachine.freetogamecom.io.remote

import com.blitzmachine.freetogamecom.io.classes.DetailedGame
import com.blitzmachine.freetogamecom.io.classes.Games
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HttpRouteController {

    @GET("games")
    fun getLiveGamesList(
        @Query("platform") platform: String?,
        @Query("category") category: String?,
        @Query("sort-by") sortBy: String?): Call<List<Games>>

    @GET("game")
    fun getGameDetails(
        @Query("id") id: Int): Call<DetailedGame>

    @GET("filter")
    fun getFilteredGameList(
        @Query("tag") category: String?,
        @Query("platform") platform: String?
    ): Call<List<Games>>
}
