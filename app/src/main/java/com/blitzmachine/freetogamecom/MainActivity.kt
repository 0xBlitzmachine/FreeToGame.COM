package com.blitzmachine.freetogamecom

import android.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blitzmachine.freetogamecom.databinding.ActivityMainBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.blitzmachine.freetogamecom.views.fragments.FavoriteFragmentDirections
import com.blitzmachine.freetogamecom.views.fragments.StartFragmentDirections

class MainActivity : AppCompatActivity() {

    private val mainActivityLayoutBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityLayoutBinding.root)

        window.statusBarColor = this.getColor(R.color.black)

        navController = (supportFragmentManager.findFragmentById(mainActivityLayoutBinding.fragmentContainerView.id) as NavHostFragment).navController
        mainActivityLayoutBinding.bottomNavigationView.setupWithNavController(navController)

        gameViewModel.listOfNewGame.observe(this) { games ->
            try {
                if (gameViewModel.cachedGames.value?.isEmpty() == true) {
                    gameViewModel.cacheGames(games)
                } else {
                    val cachedGames = gameViewModel.cachedGames.value
                    if (cachedGames != null) {
                        for (game in games) {
                            if (cachedGames.any { cachedGame ->
                                    cachedGame.game_url == game.game_url &&
                                            cachedGame.thumbnail == game.thumbnail &&
                                            cachedGame.genre == game.genre &&
                                            cachedGame.short_description == game.short_description &&
                                            cachedGame.developer == game.developer &&
                                            cachedGame.release_date == game.release_date &&
                                            cachedGame.publisher == game.publisher &&
                                            cachedGame.platform == game.platform &&
                                            cachedGame.title == game.title
                                }) {
                                continue
                            } else {
                                if (cachedGames.find { cachedGame -> cachedGame.id == game.id }?.isLiked == true) {
                                    gameViewModel.cacheGame(game.copy(isLiked = true))
                                } else {
                                    gameViewModel.cacheGame(game)
                                }
                            }
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e("MainThread", "Failed caching! ${ex.message}")
            }
        }

        gameViewModel.detailsOfGame.observe(this) {
            if (navController.currentDestination?.id == R.id.startFragment) {
                navController.navigate(StartFragmentDirections.actionStartFragmentToDetailFragment())
            } else {
                navController.navigate(FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment())
            }
        }
    }
}