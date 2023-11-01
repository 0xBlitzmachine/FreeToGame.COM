package com.blitzmachine.freetogamecom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blitzmachine.freetogamecom.databinding.ActivityMainBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.blitzmachine.freetogamecom.views.fragments.StartFragmentDirections
import com.blitzmachine.freetogamecom.views.fragments.UiViewModel

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
            if (gameViewModel.cachedGames.value?.isEmpty() == true) {
                games.forEach { game ->
                    gameViewModel.cacheGame(game)
                }
            } else {
                games.forEach { newGame ->
                    for (oldGame in gameViewModel.cachedGames.value!!) {
                        if (oldGame.id == newGame.id &&
                            oldGame.game_url == newGame.game_url &&
                            oldGame.genre == newGame.genre &&
                            oldGame.platform == newGame.platform &&
                            oldGame.developer == newGame.developer &&
                            oldGame.publisher == newGame.publisher &&
                            oldGame.release_date == newGame.release_date &&
                            oldGame.short_description == newGame.short_description &&
                            oldGame.thumbnail == newGame.thumbnail
                        ) {
                            continue
                        } else {
                            gameViewModel.cacheGame(newGame)
                        }
                    }
                }
            }
        }

        gameViewModel.detailsOfGame.observe(this) {
            navController.navigate(StartFragmentDirections.actionStartFragmentToDetailFragment())
        }
    }
}