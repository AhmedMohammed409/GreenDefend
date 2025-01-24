package com.example.greendefend.ui.homing

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.greendefend.Constants
import com.example.greendefend.R
import com.example.greendefend.data.repository.DataStorePrefrenceImpl
import com.example.greendefend.databinding.ActivityHomeBinding
import com.example.greendefend.domin.useCase.viewModels.AuthViewModel
import com.example.greendefend.ui.authentication.AuthenticationActivity
import com.example.greendefend.utli.NetworkResult
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
private val tag="HomeActivity"
    lateinit var binding: ActivityHomeBinding
    private val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var dataStorePrefrenceImpl: DataStorePrefrenceImpl

    private val toggle by lazy {
        ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close) }
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking { getdata()}
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.fragment_container)
        binding.bottomNavigationView.setupWithNavController(navController)
       binding.navigationView.setNavigationItemSelectedListener(this)
        binding.navigationView.setupWithNavController(navController)

        //action at any fragment and change
        navController.addOnDestinationChangedListener { _, destination, _ ->
            hidenFragment(destination.id)
        }
        //connection drawwer
        binding.txtAppName.text = Constants.provideProjectName(this)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        // add info header
           updateHeadearDrawer()

    }

    private fun hidenFragment(destinationId: Int) {
        when (destinationId) {

            R.id.weatherFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.checkingFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.changeProfileFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.askingFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.postFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            R.id.diagnosticResultsFragment -> {
                binding.bottomNavigationView.isVisible = false
            }
            R.id.changePasswordFragment -> {
                binding.bottomNavigationView.isVisible = false
            }

            else -> {
                binding.bottomNavigationView.isVisible = true
            }
        }
    }
     fun logoutAndObserve() {
        authViewModel.logout(Constants.Id)
        authViewModel.response.observe(this) { response ->
            binding.progressBar.visibility=View.GONE
       runBlocking { logut() }
            when (response) {
                is NetworkResult.Success -> {

                    Log.i(tag, response.data.toString())
                  Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()

                }

                is NetworkResult.Error -> {
                    Log.i(tag, response.errMsg + response.code)
//                    Toast.makeText(this, "Failed Logout", Toast.LENGTH_SHORT).show()

                }

            }
            startActivity(Intent(this, AuthenticationActivity::class.java))
            finish()
        }

    }
    fun logut(){
        lifecycleScope.launch {
            dataStorePrefrenceImpl.clearAllPreference()
            dataStorePrefrenceImpl.putPreference(
                DataStorePrefrenceImpl.onboardingOpenedState_Key, true
            )
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
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

     @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)}
        else{
            @Suppress("DEPRECATION")
            super.onBackPressed()
        }
    }
 fun updateHeadearDrawer(){
    val header = binding.navigationView.getHeaderView(0)
    val userNameText = header.findViewById<TextView>(R.id.txt_username)
    val gmailText = header.findViewById<TextView>(R.id.txt_Email)
    val imageUser=header.findViewById<ImageView>(R.id.img_user)
    userNameText.text = Constants.Name
    gmailText.text = Constants.Email
    Glide.with(this).load(Constants.imageUrl).into(imageUser)
}

}



