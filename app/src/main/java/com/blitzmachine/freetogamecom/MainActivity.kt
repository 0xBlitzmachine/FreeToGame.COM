package com.blitzmachine.freetogamecom

import android.app.ActionBar.LayoutParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.LayoutDirection
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blitzmachine.freetogamecom.databinding.ActivityMainBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.blitzmachine.freetogamecom.views.UiViewModel
import com.blitzmachine.freetogamecom.views.fragments.FavoriteFragmentDirections
import com.blitzmachine.freetogamecom.views.fragments.StartFragmentDirections

class MainActivity : AppCompatActivity() {

    private val mainActivityLayoutBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val gameViewModel: GameViewModel by viewModels()
    private val uiViewModel: UiViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityLayoutBinding.root)

        window.statusBarColor = this.getColor(R.color.black)

        navController = (supportFragmentManager.findFragmentById(mainActivityLayoutBinding.fragmentContainerView.id) as NavHostFragment).navController
        mainActivityLayoutBinding.bottomNavigationView.setupWithNavController(navController)

        gameViewModel.displayCriticalError.observe(this) { result ->
            when (result) {
                true -> {
                    uiViewModel.enableErrorScreen(gameViewModel.criticalTitle.value!!, gameViewModel.criticalMessage.value!!)
                }
                false -> {
                    uiViewModel.enableSuccessScreen()
                }
            }
        }

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

        mainActivityLayoutBinding.errorBackButton.setOnClickListener {
            uiViewModel.enableSuccessScreen()
            gameViewModel.getFilteredCachedGames(
                setOf(
                    "PC (Windows)",
                    "Web Browser",
                    "PC (Windows), Web Browser"))
        }

        uiViewModel.showSuccessLayout.observe(this) { result ->
            when (result) {
                true -> {
                    mainActivityLayoutBinding.successRootLayout.visibility = View.VISIBLE
                }
                false -> {
                    mainActivityLayoutBinding.successRootLayout.visibility = View.INVISIBLE
                }
            }
        }

        uiViewModel.showErrorLayout.observe(this) { result ->
            when (result) {
                true -> {
                    mainActivityLayoutBinding.errorRootLayout.visibility = View.VISIBLE
                    mainActivityLayoutBinding.errorTitleTextView.text = uiViewModel.errorTitle.value
                    mainActivityLayoutBinding.errorMessageTextView.text = uiViewModel.errorMessage.value
                }
                false -> {
                    mainActivityLayoutBinding.errorRootLayout.visibility = View.INVISIBLE
                    mainActivityLayoutBinding.errorTitleTextView.text = ""
                    mainActivityLayoutBinding.errorMessageTextView.text = ""
                }
            }
        }

        uiViewModel.showLoadingLayout.observe(this) { result ->
            when (result) {
                true -> {
                    mainActivityLayoutBinding.loadingRootLayout.visibility = View.VISIBLE
                }
                false -> {
                    mainActivityLayoutBinding.loadingRootLayout.visibility = View.INVISIBLE
                }
            }
        }

        gameViewModel.detailsOfGame.observe(this) {
            if (navController.currentDestination?.id == R.id.startFragment) {
                uiViewModel.enableLoadingScreen()
                navController.navigate(StartFragmentDirections.actionStartFragmentToDetailFragment())
            } else {
                uiViewModel.enableLoadingScreen()
                navController.navigate(FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment())
            }
        }
    }
}