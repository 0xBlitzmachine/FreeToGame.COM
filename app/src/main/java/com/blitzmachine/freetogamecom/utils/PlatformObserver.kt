package com.blitzmachine.freetogamecom.utils

import androidx.lifecycle.LiveData
import com.blitzmachine.freetogamecom.views.GameViewModel

class PlatformObserver(viewModel: GameViewModel): LiveData<Set<String>>() {
    init {
        viewModel.cachedGames.observeForever { cachedGames ->
            cachedGames?.let { games ->
                val collection = mutableSetOf<String>()
                for (game in games) {
                    if (collection.contains(game.platform)) {
                        continue
                    }
                    collection += game.platform
                }
                value = collection
            }
        }
    }
}