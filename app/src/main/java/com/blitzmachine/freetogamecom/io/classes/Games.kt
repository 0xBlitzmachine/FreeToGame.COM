package com.blitzmachine.freetogamecom.io.classes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Games(
    @PrimaryKey
    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String,
    val platform: String,
    val genre: String,
    val publisher: String,
    val developer: String,
    val release_date: String,
    val game_url: String,
    val isLiked: Boolean = false
)