package com.example.greendefend.ui.homing

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.os.BuildCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.greendefend.Constants
import com.example.greendefend.R
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.databinding.ActivityHomeBinding
import com.example.greendefend.domin.useCase.viewModels.AuthViewModel
import com.example.greendefend.ui.authentication.AuthenticationActivity
import com.example.greendefend.ui.homing.home.HomeFragment
import com.example.greendefend.utli.NetworkResult
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var dataStorePrefrenceImpl: DataStorePrefrenceImpl
    private val actiotogle by lazy {
        ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runBlocking { getdata() }

        //connection button navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
//        val appBarConfiguration= AppBarConfiguration(navController.graph,binding.drawer)
//        setupActionBarWithNavController(na)

        //connection drawwer
        binding.txtAppName.text = Constants.provideProjectName(this)
        binding.drawerLayout.addDrawerListener(actiotogle)
        actiotogle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.nav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "home", Toast.LENGTH_SHORT).show()
                    binding.bottomNavigationView.findNavController().navigate(R.id.homeFragment)
                }

                R.id.nav_forum -> {
                    Navigation.findNavController(this, R.id.nav_forum)
                }

                R.id.nav_profile -> {
                    Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show()
                }

                R.id.nav_forum -> {
                    Navigation.findNavController(this, R.id.nav_forum)
                }

                R.id.nav_logout -> {
                    logoutAndObserve()
                }

            }
            true
        }


        //action at any fragment and change
        navController.addOnDestinationChangedListener { _, destination, _ ->
            hidenFragment(destination.id)
        }


        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                // Back is pressed... Finishing the activity
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                    binding.drawerLayout.closeDrawer(GravityCompat.START)

                }

                finish()
            }
        } else {
            onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // Back is pressed... Finishing the activity
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    finish()
                }
            })
        }

    }



//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (actiotogle.onOptionsItemSelected(item)) {
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private fun hidenFragment(destinationId: Int) {
        when (destinationId) {
            R.id.searchFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.weatherFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.checkingFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.changeProfileFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            else -> {
                binding.bottomNavigationView.isVisible = true
            }
        }
    }

    private fun getdata() {
        lifecycleScope.launch(Dispatchers.IO) {
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.userId_KEY, "")
                    .collect {
                        Constants.Id = it
                    }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.Name_KEY, "").collect {
                    Constants.Name = it
                }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.Email_KEY, "").collect {
                    Constants.Email = it

                }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.Token_KEY, "").collect {
                    Constants.Token = it

                }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.Country_KEY, "")
                    .collect {
                        Constants.Country = it

                    }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.Bio_KEY, "").collect {
                    Constants.Bio = it
                }
            }
            launch {
                dataStorePrefrenceImpl.getPreference(DataStorePrefrenceImpl.ImageUrl_KEY, "")
                    .collect {
                        Constants.imageUrl = it.toUri()

                    }
            }

        }


    }

    private fun logoutAndObserve() {
        authViewModel.logout(Constants.Id)
        authViewModel.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    lifecycleScope.launch {
                        dataStorePrefrenceImpl.clearAllPreference()
                        dataStorePrefrenceImpl.putPreference(
                            DataStorePrefrenceImpl.onboardingOpenedState_Key,
                            true
                        )
                    }
                    startActivity(Intent(this, AuthenticationActivity::class.java))
                    finish()

                }

                is NetworkResult.Error -> {
                    Log.e("error msg logout", response.errMsg + response.code)
                    Toast.makeText(this, "Failed Logout", Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Exception -> {}
            }
        }
    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//      val itemId=item.itemId
//        if (itemId==R.id.nav_home){
//            fragmentManager.beginTransaction().replace(binding.fragment.id,object home:HomeFragment)
//        }
//    }


}


