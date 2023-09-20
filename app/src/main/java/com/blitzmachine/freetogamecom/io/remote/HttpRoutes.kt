package com.blitzmachine.freetogamecom.io.remote

import com.blitzmachine.freetogamecom.io.classes.Game
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HttpRoutes {

    @GET("games")
    fun getLiveGamesList(
        @Query("platform") platform: String?,
        @Query("category") category: String?,
        @Query("sort-by") sortBy: String?): Call<List<Game>>
}