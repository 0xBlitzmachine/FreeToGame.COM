package com.blitzmachine.freetogamecom.io.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.blitzmachine.freetogamecom.io.classes.Games

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: Games)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGames(games: List<Games>)

}