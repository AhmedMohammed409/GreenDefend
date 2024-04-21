package com.example.greendefend.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.greendefend.viewmodels.ViewModelDataStore
import com.example.greendefend.ui.authentication.AuthenticationActivity
import com.example.greendefend.ui.boarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModelDataStore: ViewModelDataStore by lazy{
        ViewModelProvider(this)[ViewModelDataStore::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        viewModelDataStore.get("sureOnBoarding")
        viewModelDataStore.value.observe(this) {
            if (it!="true"){
                startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this@MainActivity, AuthenticationActivity::class.java))
                finish()

            }
        }


    }













    }

