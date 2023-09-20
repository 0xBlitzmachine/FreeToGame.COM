package com.blitzmachine.freetogamecom.io.remote

import com.blitzmachine.freetogamecom.utils.APIUtils
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* Trying GSON Converter out
val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
*/

val retroFit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(APIUtils.apiBaseUrl)
    .build()

object FreeToGameAPI {
    val httpRoutes: HttpRoutes by lazy { retroFit.create(HttpRoutes::class.java) }
}