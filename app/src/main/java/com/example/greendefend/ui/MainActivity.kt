package com.example.greendefend.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.data.repository.DataStorePrefrenceImpl.Companion.onboardingOpenedState_Key
import com.example.greendefend.databinding.ActivityMainBinding
import com.example.greendefend.ui.authentication.AuthenticationActivity
import com.example.greendefend.ui.boarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var dataStoreRepositoryImpl: DataStorePrefrenceImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {

            dataStoreRepositoryImpl.getPreference(onboardingOpenedState_Key,false).collect{
                when(it){
                    true->{ startActivity(Intent(this@MainActivity, AuthenticationActivity::class.java))
                        finish()}
                    false->{
                        startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))
                        finish()
                    }

                }
            }
        }








    }


}

