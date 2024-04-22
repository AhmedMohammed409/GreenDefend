package com.example.greendefend.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.greendefend.ui.authentication.AuthenticationActivity
import com.example.greendefend.ui.boarding.DataStoreViewModel
import com.example.greendefend.ui.boarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val dataStoreViewModel: DataStoreViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        lifecycleScope.launch {
            dataStoreViewModel.readOnboardingOpened().join()

            dataStoreViewModel.onboardingOpened.observe(this@MainActivity){
                if (it==true){
                    startActivity(Intent(this@MainActivity, AuthenticationActivity::class.java))
                    finish()

                }else{
                    startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))
                    finish()
                }
            }
        }
    }


}

