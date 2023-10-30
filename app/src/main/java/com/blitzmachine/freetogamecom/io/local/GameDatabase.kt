package com.blitzmachine.freetogamecom.io.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blitzmachine.freetogamecom.io.classes.Games

@Database(entities = [Games::class], version = 1)
abstract class GameDatabase : RoomDatabase() {

    abstract fun databaseDao(): DatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            val currentInstance = INSTANCE
            if (currentInstance != null) {
                return currentInstance
            }

            synchronized(this) {
                return Room.databaseBuilder(context, GameDatabase::class.java, "game_database").build()
            }
        }
    }

}