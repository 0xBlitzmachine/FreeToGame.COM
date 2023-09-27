package com.blitzmachine.freetogamecom

import android.annotation.SuppressLint
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.window.SplashScreen
import android.window.SplashScreenView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blitzmachine.freetogamecom.databinding.ActivityMainBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.blitzmachine.freetogamecom.views.fragments.BottomSheetDetailsFragment
import com.blitzmachine.freetogamecom.views.fragments.UiViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MainActivity : AppCompatActivity() {

    private val mainActivityLayoutBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val uiViewModel: UiViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()
    private lateinit var bottomSheet: BottomSheetDetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityLayoutBinding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.dark)

        val navController = (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        mainActivityLayoutBinding.bottomNavigationView.setupWithNavController(navController)

        uiViewModel.showMainLogo.observe(this) { result ->
            when (result) {
                true -> mainActivityLayoutBinding.logoImageView.visibility = View.VISIBLE
                false -> mainActivityLayoutBinding.logoImageView.visibility = View.GONE
            }
        }

        gameViewModel.detailsOfSingleGame.observe(this) { game ->
            bottomSheet = BottomSheetDetailsFragment().apply {
                isCancelable = true
                show(supportFragmentManager, this.tag)
            }
        }
    }
}