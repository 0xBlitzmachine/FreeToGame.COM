package com.blitzmachine.freetogamecom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.window.SplashScreen
import android.window.SplashScreenView
import com.blitzmachine.freetogamecom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val activityBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityBinding.root)

        // Pass Frag Nav to Bottom Nav

    }
}