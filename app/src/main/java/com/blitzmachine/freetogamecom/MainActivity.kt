package com.blitzmachine.freetogamecom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import coil.util.Logger
import com.blitzmachine.freetogamecom.databinding.ActivityMainBinding
import com.blitzmachine.freetogamecom.views.GameViewModel
import com.blitzmachine.freetogamecom.views.fragments.StartFragmentDirections
import com.blitzmachine.freetogamecom.views.fragments.UiViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : AppCompatActivity(), ImageLoaderFactory {

    private val mainActivityLayoutBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val uiViewModel: UiViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()
    private lateinit var navController: NavController
    private val httpLoggingInterceptor: HttpLoggingInterceptor by lazy { HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    } }

    override fun newImageLoader(): ImageLoader {
        Log.d("ImageLoader", "Starting ImageLoader Builder ..")
        return ImageLoader.Builder(this)
            .okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build()
            }
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.5)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(this.cacheDir.resolve("game_thumbnails"))
                    .maxSizePercent(0.5)
                    .build()
            }
            .logger(DebugLogger())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityLayoutBinding.root)
        Coil.setImageLoader(this)

        window.statusBarColor = this.getColor(R.color.black)


        navController = (supportFragmentManager.findFragmentById(mainActivityLayoutBinding.fragmentContainerView.id) as NavHostFragment).navController
        mainActivityLayoutBinding.bottomNavigationView.setupWithNavController(navController)

        gameViewModel.detailsOfSingleGame.observe(this) { game ->
            navController.navigate(StartFragmentDirections.actionStartFragmentToDetailFragment())
        }
    }
}