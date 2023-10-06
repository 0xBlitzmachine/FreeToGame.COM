package com.blitzmachine.freetogamecom.io.remote

import androidx.core.content.ContextCompat
import com.blitzmachine.freetogamecom.utils.APIUtils
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
    val httpRoutes: HttpRouteController by lazy { retroFit.create(HttpRouteController::class.java) }
}