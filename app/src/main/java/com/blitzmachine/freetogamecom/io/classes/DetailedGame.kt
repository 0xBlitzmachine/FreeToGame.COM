package com.blitzmachine.freetogamecom.io.classes

data class DetailedGame(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val status: String,
    val short_description: String,
    val description: String,
    val game_url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val release_date: String,
    val freetogame_profile_url: String,
    val minimum_system_requirements: SystemRequirements?,
    val screenshots: List<GameScreenshots>
)