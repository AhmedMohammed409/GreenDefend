package com.example.greendefend.ui.homing

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.greendefend.Constants
import com.example.greendefend.R
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityHomeBinding
    @Inject lateinit var dataStorePrefrenceImpl:DataStorePrefrenceImpl
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            visableButtomNavigation(destination.id) }
        }

    private fun visableButtomNavigation(destinationId:Int){
        when (destinationId) {
            R.id.searchFragment -> {
                binding.bottomNavigationView.isVisible=false }
            R.id.weatherFragment -> {
                binding.bottomNavigationView.isVisible=false }
            R.id.checkingFragment -> {
                binding.bottomNavigationView.isVisible=false }
            R.id.changeProfileFragment -> {
                binding.bottomNavigationView.isVisible=false }
            else -> {  binding.bottomNavigationView.isVisible=true}
        }
    }


    }


