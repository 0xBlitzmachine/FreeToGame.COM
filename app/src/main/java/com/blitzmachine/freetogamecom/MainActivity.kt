package com.blitzmachine.freetogamecom

import android.annotation.SuppressLint
import android.content.res.Resources
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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
   // private lateinit var detailBottomSheet: BottomSheetDetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityLayoutBinding.root)

        window.statusBarColor = this.getColor(R.color.black)

        val navController = (supportFragmentManager.findFragmentById(mainActivityLayoutBinding.fragmentContainerView.id) as NavHostFragment).navController
        mainActivityLayoutBinding.bottomNavigationView.setupWithNavController(navController)

        //
        mainActivityLayoutBinding.bottomNavigationView.setOnItemSelectedListener {
            if (it.itemId == R.id.startFragment && navController.currentDestination?.id == R.id.detailFragment) {
                navController.popBackStack()
                Toast.makeText(this, "Home ausgewählt aber Detail war offen - Closed", Toast.LENGTH_SHORT).show()
            } else if (it.itemId == navController.currentDestination?.id) {
                // Do nothing?
            } else {
                navController.navigate(it.itemId)
            }
            true
        }
        setSupportActionBar(mainActivityLayoutBinding.materialToolbar)

        gameViewModel.detailsOfSingleGame.observe(this) { game ->
            /*detailBottomSheet = BottomSheetDetailsFragment().apply {
                this.isCancelable = true
            }.also {
                it.show(supportFragmentManager, it.tag)
            }*/
            navController.navigate(StartFragmentDirections.actionStartFragmentToDetailFragment())
        }
    }
}