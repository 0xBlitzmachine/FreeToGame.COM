package com.blitzmachine.freetogamecom.io.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.blitzmachine.freetogamecom.io.classes.Game

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<Game>)

    @Upsert
    suspend fun upsertGame(game: Game)

    @Query("SELECT * FROM games")
    fun getGames(): LiveData<List<Game>>

    @Query("SELECT * FROM games WHERE platform IN (:platform)")
    suspend fun getFilteredGames(platform: Set<String>): List<Game>

    @Query("SELECT * FROM games WHERE platform IN (:platform) AND genre in (:genre)")
    suspend fun getFilteredGames(platform: Set<String>, genre: Set<String>): List<Game>

}