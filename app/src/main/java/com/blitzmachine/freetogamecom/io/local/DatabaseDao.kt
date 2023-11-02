package com.blitzmachine.freetogamecom.io.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.blitzmachine.freetogamecom.io.classes.Game

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

}