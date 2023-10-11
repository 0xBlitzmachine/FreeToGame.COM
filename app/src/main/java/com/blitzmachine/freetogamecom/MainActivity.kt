package com.blitzmachine.freetogamecom

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import android.window.SplashScreen
import android.window.SplashScreenView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.blitzmachine.freetogamecom.databinding.ActivityMainBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.blitzmachine.freetogamecom.views.fragments.BottomSheetDetailsFragment
import com.blitzmachine.freetogamecom.views.fragments.DetailFragment
import com.blitzmachine.freetogamecom.views.fragments.StartFragmentDirections
import com.blitzmachine.freetogamecom.views.fragments.UiViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers

class MainActivity : AppCompatActivity() {

    private val mainActivityLayoutBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val uiViewModel: UiViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityLayoutBinding.root)

        window.statusBarColor = this.getColor(R.color.black)

        navController = (supportFragmentManager.findFragmentById(mainActivityLayoutBinding.fragmentContainerView.id) as NavHostFragment).navController
        mainActivityLayoutBinding.bottomNavigationView.setupWithNavController(navController)

        gameViewModel.detailsOfSingleGame.observe(this) { game ->
            navController.navigate(StartFragmentDirections.actionStartFragmentToDetailFragment())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp()
    }
}