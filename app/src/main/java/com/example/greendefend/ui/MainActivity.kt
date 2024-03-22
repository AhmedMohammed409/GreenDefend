package com.example.greendefend.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.greendefend.data.viewmodel.ViewModelDataStore
import com.example.greendefend.ui.authentication.AuthenticationActivity
import com.example.greendefend.ui.boarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private val viewModelDataStore: ViewModelDataStore by lazy{
        ViewModelProvider(this)[ViewModelDataStore::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        lifecycleScope.launch {    viewModelDataStore.getValue("sureOnboarding").observe(this@MainActivity) {

            if (it!=true.toString()){
                startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this@MainActivity, AuthenticationActivity::class.java))
                finish()

            }
        }}




        }











    }

