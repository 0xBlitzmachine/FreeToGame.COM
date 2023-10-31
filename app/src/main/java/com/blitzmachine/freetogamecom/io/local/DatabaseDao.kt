package com.blitzmachine.freetogamecom.io.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blitzmachine.freetogamecom.io.classes.Games

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Games)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<Games>)

    @Query("SELECT * FROM games")
    fun getGames(): LiveData<List<Games>>

}