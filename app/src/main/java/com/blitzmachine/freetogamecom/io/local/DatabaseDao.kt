package com.blitzmachine.freetogamecom.io.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.blitzmachine.freetogamecom.io.classes.Game
import com.blitzmachine.freetogamecom.io.classes.Genre
import com.blitzmachine.freetogamecom.io.classes.Platform

@Dao
interface DatabaseDao {

    // isLiked beachten.

    // Keep ConflictStrategy on REPLACE and use own logic to filter either if
    // object has same content as the fresh one fetched by API or not.

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<Game>)

    @Upsert
    suspend fun upsertGame(game: Game)

    @Query("SELECT * FROM games")
    fun getGames(): LiveData<List<Game>>

    @Query("SELECT * FROM games WHERE (:platform IS NULL OR platform IN (:platform)) AND (:genre IS NULL OR genre IN (:genre))")
    suspend fun getFilteredGames(platform: String? = null, genre: List<String>? = null): List<Game>



}