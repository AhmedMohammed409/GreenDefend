package com.example.greendefend.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.example.greendefend.databinding.ActivityMainBinding
import com.example.greendefend.ui.authentication.AuthenticationActivity
import com.example.greendefend.ui.boarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStore:DataStore<androidx.datastore.preferences.core.Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

dataStore=createDataStore("sherdPrefrence")

        lifecycleScope.launch {
            if (readShared("sureOnboarding") == true){
                startActivity(Intent(this@MainActivity, AuthenticationActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))
                finish()
            }
        }


    }
    private suspend fun readShared(key:String):Boolean?{
        val dataStorekey= preferencesKey<Boolean>(key)
        val prefreces=dataStore.data.first()
        return prefreces[dataStorekey]
    }




}